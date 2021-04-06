package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.ErrorCodes;
import com.auction.eni_auction.dal.dao.EnchereDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchereJdbc implements EnchereDAO {
    @Override
    public void insert(Encheres enchere) throws DALException, SQLException {
        Connection cnx = JdbcTools.connect();
        String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = cnx.prepareStatement(INSERT);
            stmt.setInt(1, enchere.getutilisateur().getNoUtilisateur());
            stmt.setInt(2, enchere.getArticle().getNoArticle());
            stmt.setObject(3, new Timestamp(enchere.getDateEnchere().getTime()));
            stmt.setInt(4, enchere.getMontantEnchere());
            stmt.executeUpdate();
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodes.ERROR_SQL_INSERT);
            throw dalException;
        }
    }


    @Override
    public List<Integer> getNoArticlesByUtilisateurAndEtat(Utilisateur utilisateur, String state) throws DALException, SQLException {
        Connection cnx = JdbcTools.connect();
        List <Integer> noArticlesMatched = new ArrayList<>();

        String SELECT_BY_UTILISATEUR_AND_ETAT = "SELECT E.no_article " +
                "FROM ENCHERES E " +
                "INNER JOIN ARTICLES_VENDUS AV on E.no_article = AV.no_article " +
                "WHERE AV.etat_vente = ? AND E.no_utilisateur = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_UTILISATEUR_AND_ETAT);
            stmt.setString(1, state);
            stmt.setInt(2, utilisateur.getNoUtilisateur());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                noArticlesMatched.add(rs.getInt("no_article"));
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodes.ERROR_SQL_SELECT);
            throw dalException;
        }

        return noArticlesMatched;
    }


    @Override
    public List<Integer> getNoArticlesWonByUtilisateur(Utilisateur utilisateur) throws DALException, SQLException {
        Connection cnx = JdbcTools.connect();
        List<Integer> articlesWonByUtilisateur = new ArrayList<>();
        String SELECT_ARTICLES_WON_BY_USER =
                "SELECT t.no_article FROM ( " +
                        "SELECT AV.no_article, E.date_enchere, E.no_utilisateur, " +
                        "row_number() OVER (" +
                        "PARTITION BY AV.no_article " +
                        "ORDER BY datediff(MI, date_enchere, date_fin_encheres)) Ranking " +
                        "FROM ENCHERES E " +
                        "         INNER JOIN ARTICLES_VENDUS AV on E.no_article = AV.no_article " +
                        "WHERE AV.etat_vente = 'VE' AND E.no_utilisateur = ?) t " +
                        "WHERE Ranking = 1";
        try {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_ARTICLES_WON_BY_USER);
            stmt.setInt(1, utilisateur.getNoUtilisateur());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                articlesWonByUtilisateur.add(rs.getInt("no_article"));
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodes.ERROR_SQL_SELECT);
            throw dalException;
        }
        return articlesWonByUtilisateur;
    }


    public HashMap<Integer, Integer> getAmountAndPseudoOfBestOffer(ArticlesVendus articleVendu) throws DALException, SQLException {
        Connection cnx = JdbcTools.connect();
        HashMap<Integer, Integer> result = new HashMap<>();
        try {
            String GET_UTILISATEUR_AND_BEST_AUCTIONS = "SELECT no_utilisateur, " +
                    "       montant_enchere " +
                    "       FROM ( " +
                    "    SELECT AV.no_article, E.date_enchere, E.no_utilisateur, E.montant_enchere, " +
                    "            row_number() OVER ( " +
                    "            PARTITION BY AV.no_utilisateur " +
                    "            ORDER BY datediff(MI, date_enchere, date_fin_encheres)) Ranking " +
                    "    FROM ENCHERES E " +
                    "    INNER JOIN ARTICLES_VENDUS AV on E.no_article = AV.no_article" +
                    "    WHERE AV.no_article = ?) t " +
                    "    WHERE Ranking = 1;";
            PreparedStatement stmt = cnx.prepareStatement(GET_UTILISATEUR_AND_BEST_AUCTIONS);
            stmt.setInt(1, articleVendu.getNoArticle());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                result.put(rs.getInt("montant_enchere"), rs.getInt("no_utilisateur"));
            } else {
                result = null;
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DALException dalException = new DALException();
            dalException.addError(ErrorCodes.ERROR_SQL_SELECT);
            throw dalException;
        }
        return result;
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
    public void update(Encheres enchere) throws DALException {

    }

    @Override
    public void delete(Encheres enchere) throws DALException {

    }

}