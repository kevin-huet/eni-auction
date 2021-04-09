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
	private static final String SELECT_ONE = "SELECT no_utilisateur, no_article, date_enchere, montant_encheres FROM CATEGORIES WHERE no_utilisateur = ? AND no_article = ?";
	private static final String UPDATE = "UPDATE ENCHERES SET date_enchere = CURRENT_TIMESTAMP, montant_encheres = ? WHERE no_utilisateur = ? AND no_article = ?";
	@Override
    public Encheres insert(Encheres var) throws DALException, SQLException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			this.updatePrevious(var.getNo_article(), cnx);
	       
	        //add the new bid and update its user's credit
        	PreparedStatement pStmt3 = cnx.prepareStatement(INSERT);
        	pStmt3.setInt(1, var.getUtilisateur().getNoUtilisateur());
        	pStmt3.setInt(2, var.getArticle().getNoArticle());
        	pStmt3.setInt(3, var.getMontantEnchere());
        
        	pStmt3.executeQuery();
        	
        	this.updateLatest(cnx, var);
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
	public Encheres selectById(int articleId, int utilisateurId) throws DALException {
		Encheres enchere = null;
    	try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(SELECT_ONE);
        	pStmt.setInt(1, utilisateurId);
        	pStmt.setInt(2, articleId);
        	ResultSet rs = pStmt.executeQuery();
        	
        	while (rs.next()) {
        		enchere = new Encheres(
        				rs.getInt("no_article"),
        				rs.getInt("no_utilisateur"),
        				rs.getInt("montant_enchere"),
        				rs.getTimestamp("date_enchere").toLocalDateTime()
        			);
        	}
        } catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enchere;
	}

	@Override
	public void update(Encheres var) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			this.updatePrevious(var.getNo_article(), cnx);
			
        	PreparedStatement pStmt = cnx.prepareStatement(UPDATE);
        	pStmt.setInt(1, var.getMontantEnchere());
        	pStmt.setInt(2, var.getNo_utilisateur());
        	pStmt.setInt(3, var.getNo_article());
        	pStmt.executeQuery();
        		
        	this.updateLatest(cnx, var);
        } catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int articleId, int utilisateurId) throws DALException {
		// TODO Auto-generated method stub
		
	}
	
	public void updatePrevious(int articleId, Connection cnx) throws SQLException {
		//get the previous top bid and refund its user's credit
        PreparedStatement pStmt = cnx.prepareStatement(SELECT_TOP);
        pStmt.setInt(1, articleId);
        ResultSet rs = pStmt.executeQuery();
        	
        while (rs.next()) {
        	int montant = rs.getInt(1);
        	int userId = rs.getInt(2);
        	StringBuilder statement = new StringBuilder();
        	statement.append("UPDATE UTILISATEURS SET credit = credit + ");
        	statement.append(montant);
        	statement.append(" WHERE no_utilisateur = ?");
        	PreparedStatement pStmt2 = cnx.prepareStatement(statement.toString());
        	pStmt2.setInt(1, userId);
        	pStmt2.executeQuery();
        }
	}
	
	public void updateLatest(Connection cnx, Encheres var) throws SQLException {
		StringBuilder statement2 = new StringBuilder();
    	statement2.append("UPDATE UTILISATEURS SET credit = credit - ");
    	statement2.append(var.getMontantEnchere());
    	statement2.append(" WHERE no_utilisateur = ?");
    	PreparedStatement pStmt4 = cnx.prepareStatement(statement2.toString());
    	pStmt4.setInt(1, var.getNo_utilisateur());
    	pStmt4.executeQuery();
	}

}