package com.auction.eni_auction.bo;

import java.util.Date;

public class Encheres {

    public Encheres(Utilisateur utilisateur, ArticlesVendus article, int _montantEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        montantEnchere = _montantEnchere;
        dateEnchere =  new Date();
    }

    private Utilisateur utilisateur;

    private ArticlesVendus article;

    private Date dateEnchere;

    private int montantEnchere;

    public Utilisateur getutilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ArticlesVendus getArticle() {
        return article;
    }

    public void setArticle(ArticlesVendus article) {
        this.article = article;
    }

    public Date getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }
}
