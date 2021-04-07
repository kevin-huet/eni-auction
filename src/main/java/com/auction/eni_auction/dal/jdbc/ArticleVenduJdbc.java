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
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Retraits;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.ArticleVenduDAO;

public class ArticleVenduJdbc implements ArticleVenduDAO{
	private static final String SELECT_BASE = "SELECT art.no_article, art.nom_article, art.description, art.date_debut_encheres, art.date_fin_encheres, art.prix_initial, art.prix_vente, art.no_utilisateur, art.no_categorie,c.libelle,e.date_enchere,e.montant_enchere,e.no_utilisateur,r.code_postal,r.rue,r.ville FROM ARTICLES_VENDUS art INNER JOIN CATEGORIES c ON art.no_categorie = c.no_categorie INNER JOIN ENCHERES e ON art.no_article = e.no_article INNER JOIN UTILISATEURS u ON art.no_utilisateur = u.no_utilisateur INNER JOIN RETRAITS r ON art.no_article = r.no_article";
	private static final String INSERT_ONE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String ORDER_BY = " ORDER BY e.date_enchere DESC";
	private static final String SELECT_USER = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = ?";
	
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
		ArticlesVendus article = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE no_article = ?");
			statement.append(ORDER_BY);
			
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	pStmt.setInt(1, id);
        	ResultSet rs = pStmt.executeQuery();
        	
        	while(rs.next()) {
        		Categorie categorie = new Categorie(rs.getInt(9), rs.getString(10));
        		PreparedStatement pStmt2 = cnx.prepareStatement(SELECT_USER);
        		pStmt2.setInt(1, rs.getInt(8));
        		ResultSet rs2 = pStmt2.executeQuery();
        		Utilisateur seller = null;
        		boolean first = true;
        		while (rs2.next() && first) {
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
        		
        		article = new ArticlesVendus(
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
        		
        		Encheres enchere = new Encheres(
        				article,
        				buyer,
        				rs.getInt(12),
        				rs.getDate(11).toLocalDate()
        		);
        		
        		article.setEnchere(enchere);
        		
        		Retraits retrait = new Retraits(article, rs.getString(15), rs.getString(14), rs.getString(16));
        		article.setRetrait(retrait);
        		
        		first = false;
        	}
        	
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
		return article;
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
