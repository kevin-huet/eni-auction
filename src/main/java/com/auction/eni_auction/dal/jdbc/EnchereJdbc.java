package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Retrait;
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
	private static final String SELECT_TOP = "SELECT montant_enchere, no_utilisateur FROM ENCHERES WHERE no_article = ? ORDER BY montant_enchere DESC LIMIT 1";
	private static final String SELECT_BASE = "SELECT art.no_article, art.nom_article, art.description, art.date_debut_encheres, art.date_fin_encheres, art.prix_initial, art.prix_vente, art.no_utilisateur, art.no_categorie,c.libelle,e.date_enchere,e.montant_enchere,e.no_utilisateur FROM ARTICLES_VENDUS art INNER JOIN CATEGORIES c ON art.no_categorie = c.no_categorie LEFT JOIN ENCHERES e ON art.no_article = e.no_article LEFT JOIN UTILISATEURS u ON art.no_utilisateur = u.no_utilisateur";
	private static final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	private static final String SELECT_ONE = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = ? AND no_article = ?";
	private static final String UPDATE = "UPDATE ENCHERES SET date_enchere = CURRENT_TIMESTAMP, montant_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
	private static final String SELECT_USER = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = ?";
	
	@Override
	public Enchere insert(Enchere var) throws DALException, SQLException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			this.updatePrevious(var.getArticle().getNoArticle(), cnx);

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
	public Enchere selectById(int articleId, int utilisateurId) throws DALException {
		Enchere enchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE e.no_article = ? AND e.no_utilisateur = ?");
			
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	pStmt.setInt(1, articleId);
        	pStmt.setInt(2, utilisateurId);
        	ResultSet rs = pStmt.executeQuery();
        	ArticleVendu article = null;
        	while(rs.next()) {
        		Categorie categorie = new Categorie(rs.getInt(9), rs.getString(10));
        		PreparedStatement pStmt2 = cnx.prepareStatement(SELECT_USER);
        		pStmt2.setInt(1, rs.getInt(8));
        		ResultSet rs2 = pStmt2.executeQuery();
        		Utilisateur seller = null;
        		
        		while (rs2.next()) {
        			seller = new Utilisateur(
        					rs2.getInt("no_utilisateur"),
        					rs2.getString("pseudo"),
        					rs2.getString("nom"),
        					rs2.getString("prenom"),
        					rs2.getString("email"),
        					rs2.getString("telephone"),
        					rs2.getString("rue"),
        					rs2.getString("code_postal"),
        					rs2.getString("ville"),
            				rs2.getString("mot_de_passe"),
            				rs2.getInt("credit"),
            				rs2.getBoolean("administrateur")
            			);
        		}
        		
        		article = new ArticleVendu(
        				rs.getInt(1),
        				rs.getString(2),
        				rs.getString(3),
        				rs.getDate(4).toLocalDate(),
        				rs.getDate(5).toLocalDate(),
        				rs.getInt(6),
        				rs.getInt(7),
        				seller,
        				categorie
        		);
        		
        		PreparedStatement pStmt3 = cnx.prepareStatement(SELECT_USER);
        		pStmt3.setInt(1, rs.getInt(13));
        		ResultSet rs3 = pStmt3.executeQuery();
        		Utilisateur buyer = null;
        		while (rs3.next()) {
        			buyer = new Utilisateur(
        					rs3.getInt("no_utilisateur"),
        					rs3.getString("pseudo"),
        					rs3.getString("nom"),
        					rs3.getString("prenom"),
        					rs3.getString("email"),
        					rs3.getString("telephone"),
        					rs3.getString("rue"),
        					rs3.getString("code_postal"),
        					rs3.getString("ville"),
        					rs3.getString("mot_de_passe"),
        					rs3.getInt("credit"),
        					rs3.getBoolean("administrateur")
            			);
        		}
        		
        		enchere = new Enchere(
        				article,
        				buyer,
        				rs.getInt(12),
        				rs.getTimestamp(11).toLocalDateTime()
        		);
        		
        		article.setEnchere(enchere);
        		
        		Retrait retrait = new Retrait(article, rs.getString(15), rs.getString(14), rs.getString(16));
        		article.setRetrait(retrait);
        	}
        	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enchere;
	}

	@Override
	public void update(Enchere var) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			this.updatePrevious(var.getArticle().getNoArticle(), cnx);

			PreparedStatement pStmt = cnx.prepareStatement(UPDATE);
			pStmt.setInt(1, var.getMontantEnchere());
			pStmt.setInt(2, var.getUtilisateur().getNoUtilisateur());
			pStmt.setInt(3, var.getArticle().getNoArticle());
			pStmt.executeQuery();

			this.updateLatest(cnx, var);
		} catch (SQLException e) {
			e.printStackTrace();
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

	public void updateLatest(Connection cnx, Enchere var) throws SQLException {
		StringBuilder statement2 = new StringBuilder();
		statement2.append("UPDATE UTILISATEURS SET credit = credit - ");
		statement2.append(var.getMontantEnchere());
		statement2.append(" WHERE no_utilisateur = ?");
		PreparedStatement pStmt4 = cnx.prepareStatement(statement2.toString());
		pStmt4.setInt(1, var.getUtilisateur().getNoUtilisateur());
		pStmt4.executeQuery();
	}

}