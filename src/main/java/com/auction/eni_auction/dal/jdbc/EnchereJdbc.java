package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.ErrorCodes;
import com.auction.eni_auction.dal.dao.EnchereDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchereJdbc implements EnchereDAO {
	private static final String SELECT_TOP = "SELECT TOP 1 montant_enchere, no_utilisateur FROM ENCHERES WHERE no_article = ? ORDER BY montant_enchere DESC";
	private static final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	
	@Override
    public Encheres insert(Encheres var) throws DALException, SQLException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//get the previous top bid and refund its user's credit
	        PreparedStatement pStmt = cnx.prepareStatement(SELECT_TOP);
	        pStmt.setInt(1, var.getArticle().getNoArticle());
	        ResultSet rs = pStmt.executeQuery();
	        	
	        while (rs.next()) {
	        	int montant = rs.getInt(1);
	        	int userId = rs.getInt(2);
	        	StringBuilder statement = new StringBuilder();
	        	statement.append("UPDATE UTILISATEURS SET credit = credit + ");
	        	statement.append(montant);
	        	statement.append(" WHERE no_utilisateur = ?");
	        	PreparedStatement pStmt2 = cnx.prepareStatement(statement.toString());
	        	pStmt2.setInt(1, var.getutilisateur().getNoUtilisateur());
	        	pStmt2.executeQuery();
	        }
	       
	        //add the new bid and update its user's credit
        	PreparedStatement pStmt3 = cnx.prepareStatement(INSERT);
        	pStmt3.setInt(1, var.getutilisateur().getNoUtilisateur());
        	pStmt3.setInt(2, var.getArticle().getNoArticle());
        	pStmt3.setInt(3, var.getMontantEnchere());
        
        	pStmt3.executeQuery();
        	
        	StringBuilder statement2 = new StringBuilder();
        	statement2.append("UPDATE UTILISATEURS SET credit = credit - ");
        	statement2.append(var.getMontantEnchere());
        	statement2.append(" WHERE no_utilisateur = ?");
        	PreparedStatement pStmt4 = cnx.prepareStatement(statement2.toString());
        	pStmt4.setInt(1, var.getutilisateur().getNoUtilisateur());
        	pStmt4.executeQuery();
        	var.getutilisateur().setCredit(var.getutilisateur().getCredit() - var.getMontantEnchere());
        	var.getArticle().setEnchere(var);
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return var;
    }

    @Override
    public Encheres selectById(int id) throws DALException {
        return null;
    }

    @Override
    public List<Encheres> selectAll() throws DALException {
        return null;
    }

    @Override
    public void update(Encheres var) throws DALException {

    }

    @Override
    public void delete(int id) throws DALException {

    }

    @Override
    public List<Integer> getNoArticlesByUtilisateurAndEtat(Utilisateur utilisateur, String state) throws DALException, SQLException {
        return null;
    }

    @Override
    public List<Integer> getNoArticlesWonByUtilisateur(Utilisateur utilisateur) throws DALException, SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, Integer> getAmountAndPseudoOfBestOffer(ArticlesVendus articleVendu) throws DALException, SQLException {
        return null;
    }
}