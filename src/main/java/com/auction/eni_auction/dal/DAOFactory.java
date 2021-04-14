package com.auction.eni_auction.dal;

import com.auction.eni_auction.dal.dao.ArticleVenduDAO;
import com.auction.eni_auction.dal.dao.CategorieDAO;
import com.auction.eni_auction.dal.dao.EnchereDAO;
import com.auction.eni_auction.dal.dao.RetraitDAO;
import com.auction.eni_auction.dal.dao.UtilisateurDAO;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

public class DAOFactory {

    public static ArticleVenduDAO getArticleDAO() {
        return new ArticleVenduJdbc();
    }

    public static CategorieDAO getCategorieDAO() {
        return new CategorieJdbc();
    }

    public static EnchereDAO getEnchereDAO() {
        return new EnchereJdbc();
    }

    public static RetraitDAO getRetraitDAO() {
        return new RetraitJdbc();
    }

    public static UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurJdbc();
    }
}