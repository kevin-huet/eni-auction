package com.auction.eni_auction.bo;

import java.util.Date;

public class ArticlesVendus {

    private int noArticle;

    private String nom;

    private String description;

    private Date beginAuction;

    private Date endAuction;

    private int initialPrice;

    private int sellPrice;

    private int noUtilisateur;

    private int noCategorie;

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBeginAuction() {
        return beginAuction;
    }

    public void setBeginAuction(Date beginAuction) {
        this.beginAuction = beginAuction;
    }

    public Date getEndAuction() {
        return endAuction;
    }

    public void setEndAuction(Date endAuction) {
        this.endAuction = endAuction;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }
}
