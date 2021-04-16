package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Enchere addEnchere(ArticleVendu article, Utilisateur user, String price) throws BusinessException {
        BusinessException be = new BusinessException();

        if (article.getEndAuction().compareTo(LocalDate.now()) > 0 || article.getBeginAuction().compareTo(LocalDate.now()) < 0) {
            be.addError("Vous ne pouvez pas effectuer d'enchère sur ce produit.");
            throw be;
        }

        if (article.getUtilisateur().getNoUtilisateur() == user.getNoUtilisateur()) {
            be.addError("Vous ne pouvez pas effectuer d'enchère sur ce produit.");
            throw be;
        }
        try {
            int currentHighest = DAOFactory.getArticleDAO().getCurrentHighest(article.getNoArticle());
            if (Integer.parseInt(price) <= currentHighest) {
                be.addError("Le montant proposÃ© n'est pas supÃ©rieur Ã  la derniÃ¨re enchÃ¨re.");
                throw be;
            }
        } catch (DALException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }

        try {
            int currentCredit = DAOFactory.getUtilisateurDAO().getCredit(user.getNoUtilisateur());
            if (currentCredit < Integer.parseInt(price)) {
                be.addError("Vous n'avez pas suffisamment de crÃ©dit pour effectuer cette enchÃ¨re.");
                throw be;
            }
        } catch (DALException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }


        try {
            return DAOFactory.getEnchereDAO().insert(new Enchere(article, user, Integer.parseInt(price), LocalDateTime.now()));
        } catch (SQLException | NumberFormatException | DALException e) {
            e.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }
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

    public void update(Enchere enchere, String price) throws BusinessException {
        BusinessException be = new BusinessException();
        enchere.setMontantEnchere(Integer.parseInt(price));

        if (enchere.getArticle().getEndAuction().compareTo(LocalDate.now()) > 0 || enchere.getArticle().getBeginAuction().compareTo(LocalDate.now()) < 0) {
            be.addError("Vous ne pouvez pas effectuer d'enchère sur ce produit.");
            throw be;
        }

        if (enchere.getArticle().getUtilisateur().getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur()) {
            be.addError("Vous ne pouvez pas effectuer d'enchère sur ce produit.");
            throw be;
        }
        try {
            int currentHighest = DAOFactory.getArticleDAO().getCurrentHighest(enchere.getArticle().getNoArticle());
            if (enchere.getMontantEnchere() <= currentHighest) {
                be.addError("Le montant proposé n'est pas supérieur à la dernière enchère.");
                throw be;
            }
        } catch (DALException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }

        try {
            int currentCredit = DAOFactory.getUtilisateurDAO().getCredit(enchere.getUtilisateur().getNoUtilisateur());
            if (currentCredit < enchere.getMontantEnchere()) {
                be.addError("Vous n'avez pas suffisamment de crédit pour effectuer cette enchère.");
                throw be;
            }
        } catch (DALException e1) {
            e1.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }


        try {
            DAOFactory.getEnchereDAO().update(enchere);

        } catch (DALException e) {
            e.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }
    }
}
