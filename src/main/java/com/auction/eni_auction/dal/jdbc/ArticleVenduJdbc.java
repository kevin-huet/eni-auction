package com.auction.eni_auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
	private static final String ORDER_BY = " ORDER BY art.no_article DESC, e.date_enchere DESC";
	private static final String SELECT_USER = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = ?";
	private static final String UPDATE =  "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, no_categorie = ? WHERE no_article = ?";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	private static final String UPDATE_PRICE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
	private static final String SELECT_PRICE = "SELECT prix_vente FROM ARTICLES_VENDUS WHERE no_article = ?";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postale, ville) VALUES (?, ?, ?, ?)";
	
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
		} catch (Exception e) {
			e.printStackTrace();
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
        	boolean first = true;
        	
        	while(rs.next() && first) {
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
        				rs.getTimestamp(11).toLocalDateTime()
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	@Override
	public List<ArticlesVendus> selectAll() throws DALException {
		ArrayList<ArticlesVendus> articles = new ArrayList<ArticlesVendus>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(ORDER_BY);
			
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	ResultSet rs = pStmt.executeQuery();
        	
        	int lastId = 0;
        	while(rs.next()) {
        		//skip row if the article is the same, since there is a row by bid and the latest and highest bid is always the first to appear
        		if (lastId != rs.getInt(1)) {
        			lastId = rs.getInt(1);
        			
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
            		
            		ArticlesVendus article = new ArticlesVendus(
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
            				rs.getTimestamp(11).toLocalDateTime()
            		);
            		
            		article.setEnchere(enchere);
            		
            		articles.add(article);
        		}
        	}
        	
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public void update(ArticlesVendus var) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(UPDATE);
			pStmt.setString(1, var.getNom());
			pStmt.setString(2, var.getDescription());
			pStmt.setDate(3, java.sql.Date.valueOf(var.getBeginAuction()));
			pStmt.setDate(4, java.sql.Date.valueOf(var.getEndAuction()));
			pStmt.setInt(5, var.getInitialPrice());
			pStmt.setInt(6, var.getCategorie().getNoCategorie());
			pStmt.setInt(7, var.getNoArticle());
			
			pStmt.executeQuery();
		} catch (SQLException e) {
			DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(ArticlesVendus var) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	/** basic filter for the homepage
	 * @param name Filter string, empty string if no filter wanted
	 * @param categorieId Integer corresponding to the categorie we want to filter by, 0 if no filter wanted
	 */
	public List<ArticlesVendus> filterBase(String name, int categorieId) throws DALException {
		ArrayList<ArticlesVendus> articles = new ArrayList<ArticlesVendus>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE art.nom_article LIKE '%?%' AND art.date_fin_encheres >= GETDATE() AND art.date_debut_encheres <= GETDATE()");
			if (categorieId != 0) {
				statement.append(" AND art.no_categorie = ?");
			}
			statement.append(ORDER_BY);
			
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	ResultSet rs = pStmt.executeQuery();
        	
        	int lastId = 0;
        	while(rs.next()) {
        		//skip row if the article is the same, since there is a row by bid and the latest and highest bid is always the first to appear
        		if (lastId != rs.getInt(1)) {
        			lastId = rs.getInt(1);
        			
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
            		
            		ArticlesVendus article = new ArticlesVendus(
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
            				rs.getTimestamp(11).toLocalDateTime()
            		);
            		
            		article.setEnchere(enchere);
            		
            		articles.add(article);
        		}
        	}
        	
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticlesVendus> filterBuy(String name, int categorieId, String state, Utilisateur utilisateur)
			throws DALException {
		ArrayList<ArticlesVendus> articles = new ArrayList<ArticlesVendus>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE art.nom_article LIKE '%?%'");
			if (categorieId != 0) {
				statement.append(" AND art.no_categorie = ?");
			}
			if (state.equals("ouvert")) {
				statement.append(" AND art.date_fin_encheres >= GETDATE() AND art.date_debut_encheres <= GETDATE()");
			} else if (state.equals("en cours")) {
				statement.append(" AND art.date_fin_encheres >) GETDATE() AND art.date_debut_encheres <= GETDATE() AND e.no_utilisateur = ?");
			} else if (state.equals("finis")) {
				statement.append("AND art.date_fin_encheres < GETDATE() AND e.no_utilisateur = ? AND e.montant_enchere = art.prix_vente");
			}
			statement.append(ORDER_BY);
			
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	pStmt.setString(1, name);
        	int index = 2; 
        	if (categorieId != 0) {
				pStmt.setInt(index, categorieId);
				index++;
			}
        	if (state.equals("en cours")) {
				pStmt.setInt(index, utilisateur.getNoUtilisateur());
			} else if (state.equals("finis")) {
				pStmt.setInt(index, utilisateur.getNoUtilisateur());
			}
        	ResultSet rs = pStmt.executeQuery();
        	
        	articles = (ArrayList<ArticlesVendus>) this.createList(rs, cnx);
        	
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticlesVendus> filterSell(String name, int categorieId, String state, Utilisateur utilisateur)
			throws DALException {
		ArrayList<ArticlesVendus> articles = new ArrayList<ArticlesVendus>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE art.no_utilisateur = ? AND art.nom_article LIKE '%?%'");
			if (categorieId != 0) {
				statement.append(" AND art.no_categorie = ?");
			}
			if (state.equals("ouvert")) {
				statement.append(" AND art.date_fin_encheres >= GETDATE() AND art.date_debut_encheres <= GETDATE()");
			} else if (state.equals("non débuté")) {
				statement.append(" AND art.date_debut_encheres < GETDATE()");
			} else if (state.equals("finis")) {
				statement.append("AND art.date_fin_encheres < GETDATE()");
			}
			statement.append(ORDER_BY);
			
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
        	pStmt.setString(1, name);
        	int index = 2; 
        	if (categorieId != 0) {
				pStmt.setInt(index, categorieId);
				index++;
			}
        	if (state.equals("en cours")) {
				pStmt.setInt(index, utilisateur.getNoUtilisateur());
			} else if (state.equals("finis")) {
				pStmt.setInt(index, utilisateur.getNoUtilisateur());
			}
        	ResultSet rs = pStmt.executeQuery();
        	
        	articles = (ArrayList<ArticlesVendus>) this.createList(rs, cnx);
        	
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	public List<ArticlesVendus> createList(ResultSet rs, Connection cnx) throws SQLException {
		ArrayList<ArticlesVendus> articles = new ArrayList<ArticlesVendus>();
		int lastId = 0;
    	while(rs.next()) {
    		//skip row if the article is the same, since there is a row by bid and the latest and highest bid is always the first to appear
    		if (lastId != rs.getInt(1)) {
    			lastId = rs.getInt(1);
    			
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
        		
        		ArticlesVendus article = new ArticlesVendus(
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
        		
        		// if there is a bid add it
        		if (rs.getInt(12) != 0) {
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
            				rs.getTimestamp(11).toLocalDateTime()
            		);
            		
            		article.setEnchere(enchere);
        		}
        		
        		articles.add(article);
    		}
    	}
    	return articles;
	}

	@Override
	public void AddEnchere(int articleId, Encheres enchere) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(INSERT_ENCHERE);
        	pStmt.setInt(1, enchere.getutilisateur().getNoUtilisateur());
        	pStmt.setInt(2, articleId);
        	pStmt.setInt(3, enchere.getMontantEnchere());
        
        	pStmt.executeQuery();
        	
        	PreparedStatement pStmt2 = cnx.prepareStatement(UPDATE_PRICE);
        	pStmt2.setInt(1, enchere.getMontantEnchere());
        	pStmt2.setInt(2, articleId);
        	pStmt2.executeQuery();
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCurrentHighest(int articleId) throws DALException {
		int price = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(SELECT_PRICE);
        	pStmt.setInt(1, articleId);
        
        	ResultSet rs = pStmt.executeQuery();
        	while(rs.next()) {
        		price = rs.getInt(1);
        	}
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return price;
	}

	@Override
	public void addRetrait(int articleId, Retraits retrait) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(INSERT_ENCHERE);
        	pStmt.setInt(1, articleId);
        	pStmt.setString(2, retrait.getRue());
        	pStmt.setString(3, retrait.getPostalCode());
        	pStmt.setString(4, retrait.getCity());
        
        	pStmt.executeQuery();
		} catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
