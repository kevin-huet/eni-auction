package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Enchere;
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
	private static final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	private static final String SELECT_ONE = "SELECT no_utilisateur, no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = ? AND no_article = ?";
	private static final String UPDATE = "UPDATE ENCHERES SET date_enchere = CURRENT_TIMESTAMP, montant_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
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
			PreparedStatement pStmt = cnx.prepareStatement(SELECT_ONE);
			pStmt.setInt(1, utilisateurId);
			pStmt.setInt(2, articleId);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				enchere = new Enchere(
						rs.getInt("no_article"),
						rs.getInt("no_utilisateur"),
						rs.getInt("montant_enchere"),
						rs.getTimestamp("date_enchere").toLocalDateTime()
				);
			}
		} catch (SQLException e) {
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