package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVenduManager {

    private static ArticleVenduJdbc articleVenduJdbc = null;
    private static ArticleVenduManager instance = null;

    public ArticleVenduManager() {
        articleVenduJdbc = new ArticleVenduJdbc();
    }

    public static ArticleVenduManager getInstance() {
        if(instance == null) {
            instance = new ArticleVenduManager();
        }
        return instance;
    }

    public ArticleVendu addArticle(String category, String nom, String description, LocalDate endAuction, String price,
                                   int sellPrice, Utilisateur utilisateur) {
        Categorie categorie;
        try {
            categorie = CategorieManager.getInstance().getCategorie(Integer.parseInt(category));
            if (categorie != null)
                return articleVenduJdbc.insert(new ArticleVendu(nom, description, LocalDate.now(),
                        endAuction, Integer.parseInt(price), 0, utilisateur, categorie));
        } catch (DALException | NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArticleVendu editArticle() {
        return null;
    }

    public ArticleVendu removedArticle(ArticleVendu articleVendu) {
        try {
           articleVenduJdbc.delete(articleVendu.getNoArticle());
           return articleVendu;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArticleVendu getArticle(String articleId) {
        try {
            return articleVenduJdbc.selectById(Integer.parseInt(articleId));
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ArticleVendu> getBaseArticle(String cat, String search) {
        try {
            return articleVenduJdbc.filterBase(
                    (search != null && !search.equals("null")) ? search : "",
                    (cat != null && !cat.equals("null")) ? Integer.parseInt(cat) : 0
            );
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new ArrayList<ArticleVendu>();
    }

    public List<ArticleVendu> getBuyArticle(String cat, String search, Utilisateur user) {
        try {
            return articleVenduJdbc.filterBuy(
                    (search != null && !search.equals("null")) ? search : "",
                    (cat != null && !cat.equals("null")) ? Integer.parseInt(cat) : 0,
                    "ouvert", user
            );
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new ArrayList<ArticleVendu>();
    }
}
