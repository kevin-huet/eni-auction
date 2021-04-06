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

    private Utilisateur utilisateur;

    private Categorie categorie;
    
    

    public ArticlesVendus(int noArticle, String nom, String description, Date beginAuction, Date endAuction,
			int initialPrice, int sellPrice, Utilisateur utilisateur, Categorie categorie) {
		super();
		this.noArticle = noArticle;
		this.nom = nom;
		this.description = description;
		this.beginAuction = beginAuction;
		this.endAuction = endAuction;
		this.initialPrice = initialPrice;
		this.sellPrice = sellPrice;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}
    
   

	public ArticlesVendus(String nom, String description, Date beginAuction, Date endAuction, int initialPrice,
			int sellPrice, Utilisateur utilisateur, Categorie categorie) {
		super();
		this.nom = nom;
		this.description = description;
		this.beginAuction = beginAuction;
		this.endAuction = endAuction;
		this.initialPrice = initialPrice;
		this.sellPrice = sellPrice;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}



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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}