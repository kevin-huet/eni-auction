package com.auction.eni_auction.bo;

import java.util.Date;

public class Encheres {

    public Encheres(int _noUtilisateur, int _noArticle, int _montantEnchere) {
        noArticle = _noArticle;
        noUtilisateur = _noUtilisateur;
        montantEnchere = _montantEnchere;
        dateEnchere =  new Date();
    }

    private int noUtilisateur;

    private int noArticle;

    private Date dateEnchere;

    private int montantEnchere;

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
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
