package com.auction.eni_auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.ArticleVenduDAO;

public class ArticleVenduJdbc implements ArticleVenduDAO{
	private static final String SELECT_WHERE_START = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_inital, prix_vente, no_utilisateur, no_categorie FROM ARTICLES_VENDUS WHERE ";
	private static final String SELECT_CATEGORIE = "SELECT no_categorie, libelle FROM CATEGORIES";
	private static final String SELECT_ENCHERE = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES";
	private static final String SELECT_RETRAIT = "SELECT no_article, rue, code_postaln, ville FROM RETRAITS";
	private static final String INSERT_ONE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_inital, no_utilisateur, no_categorie) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	@Override
	public ArticlesVendus insert(ArticlesVendus var) throws DALException, SQLException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(INSERT_ONE, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, var.getNom());
			pStmt.setString(2, var.getDescription());
			pStmt.setDate(3, java.sql.Date.valueOf(var.getBeginAuction()));
			pStmt.setDate(4, java.sql.Date.valueOf(var.getEndAuction()));
			pStmt.setInt(5, var.getInitialPrice());
			pStmt.setInt(6, var.getUtilisateur().getNoUtilisateur());
			pStmt.setInt(7, var.getCategorie().getNoCategorie());
			pStmt.executeUpdate();
			
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				var.setNoArticle(rs.getInt(1));
			}
		} catch (SQLException e) {
			DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
		return var;
	}

	@Override
	public ArticlesVendus selectById(int id) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_WHERE_START);
			statement.append(" no_article = ?");
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	ResultSet rs = pStmt.executeQuery();
        	
        	while(rs.next()) {
        		ArticlesVendus article = null;
        	}
        	
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
		return null;
	}

	@Override
	public List<ArticlesVendus> selectAll() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ArticlesVendus var) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ArticlesVendus var) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ArticlesVendus> filterByCategory(Categorie categorie) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticlesVendus> filterByString(String filter) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> filterByEtat(String etat) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getArticlesFromASellerAndState(Utilisateur utilisateur, String state) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCurrentPrice(int noArticle, int newPrice) throws DALException {
		// TODO Auto-generated method stub
		
	}
	
}
