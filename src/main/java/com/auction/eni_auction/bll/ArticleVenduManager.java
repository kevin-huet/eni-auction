package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArticleVenduManager {

    private static ArticleVenduManager instance = null;

    public ArticleVenduManager() {

    }

    public static ArticleVenduManager getInstance() {
        if(instance == null) {
            instance = new ArticleVenduManager();
        }
        return instance;
    }

    public ArticleVendu addArticle(String category, String nom, String description, String start, String end, String price,
                                   Utilisateur utilisateur) throws BusinessException {

        BusinessException be = new BusinessException();
        Categorie categorie = null;
        boolean incomplete = false;
        LocalDate dateStart = null;
        LocalDate dateEnd = null;

        if (nom == null || nom.equals("")) {
            incomplete = true;
        }
        if (category == null || category.equals("")) {
            incomplete = true;
        } else {
            categorie = CategorieManager.getInstance().getCategorie(Integer.parseInt(category));
            if (categorie == null) {
                be.addError("La catÃ©gorie sÃ©lectionnÃ© est invalide.");
            }
        }
        if (description == null || description.equals("")) {
            incomplete = true;
        }
        if (start == null || start.equals("") || end == null || end.equals("")) {
            incomplete = true;
        } else {
            try {
                dateStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                dateEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                if (dateStart.compareTo(LocalDate.now()) < 0) {
                    be.addError("La date dÃ©but ne doit pas Ãªtre antÃ©rieur Ã  celle du jour.");
                }

                if (dateStart.compareTo(dateEnd) > 0) {
                    be.addError("La date de fin ne doit pas Ãªtre antÃ©rieur Ã  celle de dÃ©but.");
                }
            } catch (Exception e) {
                be.addError("Format de date invalide.");
            }
        }
        if (price == null || price.equals("")) {
            incomplete = true;
        } else if (Integer.parseInt(price) <= 0) {
            be.addError("Le prix doit Ãªtre un entier supÃ©rieur Ã  0.");
        }

        if (incomplete) {
            be.addError("Tout les champs sont obligatoire.");
        }

        if (be.hasErrors()) {
            throw be;
        }

        try {
            ArticleVendu article = DAOFactory.getArticleDAO().insert(new ArticleVendu(nom, description, dateStart,
                    dateEnd, Integer.parseInt(price), 0, utilisateur, categorie));
            return article;
        } catch (DALException | NumberFormatException | SQLException e) {
            e.printStackTrace();
            be.addError("Une erreur c'est produite.");
            throw be;
        }
    }

    public ArticleVendu editArticle() {
        return null;
    }

    public ArticleVendu removedArticle(ArticleVendu articleVendu) {
        try {
            DAOFactory.getArticleDAO().delete(articleVendu.getNoArticle());
            return articleVendu;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArticleVendu getArticle(String articleId) {
        try {
            return DAOFactory.getArticleDAO().selectById(Integer.parseInt(articleId));
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ArticleVendu> getBaseArticle(String cat, String search) {
        try {
            return DAOFactory.getArticleDAO().filterBase(
                    (search != null && !search.equals("null")) ? search : "",
                    (cat != null && !cat.equals("null")) ? Integer.parseInt(cat) : 0
            );
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new ArrayList<ArticleVendu>();
    }

    public List<ArticleVendu> getBuyArticle(String cat, String search, Utilisateur user, String status) {
        try {
            return DAOFactory.getArticleDAO().filterBuy(
                    (search != null && !search.equals("null")) ? search : "",
                    (cat != null && !cat.equals("null")) ? Integer.parseInt(cat) : 0,
                    status,
                    user
            );
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new ArrayList<ArticleVendu>();
    }

    public List<ArticleVendu> getSellArticle(String cat, String search, Utilisateur user, String status) {
        try {
            return DAOFactory.getArticleDAO().filterSell(
                    (search != null && !search.equals("null")) ? search : "",
                    (cat != null && !cat.equals("null")) ? Integer.parseInt(cat) : 0,
                    status,
                    user
            );
        } catch (DALException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new ArrayList<ArticleVendu>();
    }
}
