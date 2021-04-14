package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;

import java.sql.SQLException;

public class EnchereManager {

    private static EnchereManager instance = null;

    public EnchereManager() {

    }

    public static EnchereManager getInstance() {
        if(instance == null) {
            instance = new EnchereManager();
        }
        return instance;
    }

    public Enchere addEnchere(Enchere enchere) {
        try {
            return DAOFactory.getEnchereDAO().insert(enchere);
        } catch (SQLException | NumberFormatException | DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enchere editEnchere(Enchere enchere) {
        try {
        	DAOFactory.getEnchereDAO().update(enchere);
           return enchere;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enchere removeEnchere(Enchere enchere) {
        try {
        	DAOFactory.getEnchereDAO().delete(enchere.getArticle().getNoArticle(), enchere.getUtilisateur().getNoUtilisateur());
            return enchere;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enchere getEnchere(int noArticle, int noUtilisateur) {
        try {
            return DAOFactory.getEnchereDAO().selectById(noArticle, noArticle);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Enchere enchere) {
        try {
        	DAOFactory.getEnchereDAO().update(enchere);

        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
