package com.auction.eni_auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Retrait;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.ArticleVenduDAO;

public class ArticleVenduJdbc implements ArticleVenduDAO{
	private static final String SELECT_BASE = "SELECT art.no_article, art.nom_article, art.description, art.date_debut_encheres, art.date_fin_encheres, art.prix_initial, art.prix_vente, art.no_utilisateur, art.no_categorie,c.libelle,e.date_enchere,e.montant_enchere,e.no_utilisateur,r.code_postal,r.rue,r.ville FROM ARTICLES_VENDUS art LEFT JOIN CATEGORIES c ON art.no_categorie = c.no_categorie LEFT JOIN ENCHERES e ON art.no_article = e.no_article LEFT JOIN UTILISATEURS u ON art.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS r ON art.no_article = r.no_article";
	private static final String INSERT_ONE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String ORDER_BY = " ORDER BY art.no_article DESC, e.montant_enchere DESC";
	private static final String SELECT_USER = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = ?";
	private static final String UPDATE =  "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, no_categorie = ? WHERE no_article = ?";
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	private static final String UPDATE_PRICE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
	private static final String SELECT_PRICE = "SELECT TOP 1 montant_enchere FROM ENCHERES WHERE no_article = ?";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postale, ville) VALUES (?, ?, ?, ?)";
	private static final String ARTICLE_BUY = "SELECT art.no_article, art.nom_article, art.description, art.date_debut_encheres, art.date_fin_encheres, art.prix_initial, art.prix_vente, art.no_utilisateur, art.no_categorie,c.libelle,e.date_enchere,e.montant_enchere,e.no_utilisateur FROM ARTICLES_VENDUS art INNER JOIN CATEGORIES c ON art.no_categorie = c.no_categorie INNER JOIN ENCHERES e ON art.no_article = e.no_article INNER JOIN UTILISATEURS u ON art.no_utilisateur = u.no_utilisateur";
	private static final String ARTICLE_SELL = "SELECT art.no_article, art.nom_article, art.description, art.date_debut_encheres, art.date_fin_encheres, art.prix_initial, art.prix_vente, art.no_utilisateur, art.no_categorie,c.libelle,e.date_enchere,e.montant_enchere,e.no_utilisateur FROM ARTICLES_VENDUS art INNER JOIN CATEGORIES c ON art.no_categorie = c.no_categorie LEFT JOIN ENCHERES e ON art.no_article = e.no_article INNER JOIN UTILISATEURS u ON art.no_utilisateur = u.no_utilisateur";
	private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";

	public ArticleVendu insert(ArticleVendu var) throws DALException, SQLException {
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
	public ArticleVendu selectById(int id) throws DALException {
		ArticleVendu article = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE art.no_article = ?");
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

				if (rs.getInt(12) > 0) {
					Enchere enchere = new Enchere(
							article,
							buyer,
							rs.getInt(12),
							rs.getTimestamp(11).toLocalDateTime()
					);

					article.setEnchere(enchere);
				}


				if (rs.getString(15) != null) {
					Retrait retrait = new Retrait(article, rs.getString(15), rs.getString(14), rs.getString(16));
					article.setRetrait(retrait);
				}
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
	public List<ArticleVendu> selectAll() throws DALException {
		ArrayList<ArticleVendu> articles = new ArrayList<ArticleVendu>();
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
            		ArticleVendu article = new ArticleVendu(
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
            		
            		Enchere enchere = new Enchere(
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
	public void update(ArticleVendu var) throws DALException {
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
	public void delete(int id) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(DELETE);
			pStmt.setInt(1, id);

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
	/** basic filter for the homepage
	 * @param name Filter string, empty string if no filter wanted
	 * @param categorieId Integer corresponding to the categorie we want to filter by, 0 if no filter wanted
	 */
	public List<ArticleVendu> filterBase(String name, int categorieId) throws DALException {
		ArrayList<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_BASE);
			statement.append(" WHERE art.date_fin_encheres >= CURDATE() AND art.date_debut_encheres <= CURDATE()");
			if(!name.equals("")) {
				statement.append(" AND art.nom_article LIKE '%?%'");
			}
			if (categorieId != 0) {
				statement.append(" AND art.no_categorie = ?");
			}
			statement.append(ORDER_BY);

			PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
			int index = 1;
			if(!name.equals("")) {
				pStmt.setString(index, name);
				index++;
			}
			if (categorieId != 0) {
				pStmt.setInt(index, categorieId);
				index++;
			}

			ResultSet rs = pStmt.executeQuery();

			articles = (ArrayList<ArticleVendu>) this.createList(rs, cnx);
		} catch (SQLException e) {
			e.printStackTrace();
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticleVendu> filterBuy(String name, int categorieId, String state, Utilisateur utilisateur)
			throws DALException {
		ArrayList<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			if (!state.equals("ouvert")) {
				statement.append(ARTICLE_BUY);
			} else {
				statement.append(SELECT_BASE);
			}
			int index = 1;
			if(!name.equals("")) {
				statement.append(" WHERE art.nom_article LIKE '%?%'");
			}
			if (categorieId != 0) {
				statement.append(" AND art.no_categorie = ?");
			}
			if (state.equals("ouvert")) {
				statement.append(" AND art.date_fin_encheres >= CURDATE() AND art.date_debut_encheres <= CURDATE()");
			} else if (state.equals("en cours")) {
				statement.append(" AND art.date_fin_encheres > CURDATE() AND art.date_debut_encheres <= CURDATE() AND e.no_utilisateur = ?");
			} else if (state.equals("finis")) {
				statement.append("AND art.date_fin_encheres < CURDATE() AND e.no_utilisateur = ? AND e.montant_enchere = art.prix_vente");
			}
			statement.append(ORDER_BY);
			PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
			if (!name.equals("")) {
				pStmt.setString(index, name);
				index++;
			}

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
        	
        	articles = (ArrayList<ArticleVendu>) this.createList(rs, cnx);
        	
		} catch (SQLException e) {
			e.printStackTrace();
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticleVendu> filterSell(String name, int categorieId, String state, Utilisateur utilisateur)
			throws DALException {
		ArrayList<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(ARTICLE_SELL);

			statement.append(" WHERE art.no_utilisateur = ?");
			if(!name.equals("")) {
				statement.append(" AND art.nom_article LIKE '%?%'");
			}
			if (categorieId != 0) {
				statement.append(" AND art.no_categorie = ?");
			}
			if (state.equals("ouvert")) {
				statement.append(" AND art.date_fin_encheres >= CURDATE() AND art.date_debut_encheres <= CURDATE()");
			} else if (state.equals("non débuté")) {
				statement.append(" AND art.date_debut_encheres < CURDATE()");
			} else if (state.equals("finis")) {
				statement.append("AND art.date_fin_encheres < CURDATE()");
			}
			statement.append(ORDER_BY);
			System.out.println("debug test: "+statement.toString());
			PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
			pStmt.setInt(1, utilisateur.getNoUtilisateur());
			int index = 2;
			if (!name.equals("")) {
				pStmt.setString(index, name);
				index++;
			}
			if (categorieId != 0) {
				pStmt.setInt(index, categorieId);
				index++;
			}
			ResultSet rs = pStmt.executeQuery();

			articles = (ArrayList<ArticleVendu>) this.createList(rs, cnx);
		} catch (SQLException e) {
			e.printStackTrace();
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	public List<ArticleVendu> createList(ResultSet rs, Connection cnx) throws SQLException {
		ArrayList<ArticleVendu> articles = new ArrayList<ArticleVendu>();
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

				ArticleVendu article = new ArticleVendu(
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
            		
            		Enchere enchere = new Enchere(
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
	public int getCurrentHighest(int articleId) throws DALException {
		int price = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			StringBuilder statement = new StringBuilder();
			statement.append(SELECT_PRICE);
			statement.append(ORDER_BY);
        	PreparedStatement pStmt = cnx.prepareStatement(statement.toString());
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
	
}
