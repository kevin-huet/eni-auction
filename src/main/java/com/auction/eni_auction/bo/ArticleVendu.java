package com.auction.eni_auction.bo;

import java.time.LocalDate;
import java.util.Date;

public class ArticleVendu {

    private int noArticle;

    private String nom;

    private String description;

    private LocalDate beginAuction;

    private LocalDate endAuction;

    private int initialPrice;

    private int sellPrice;

    private Utilisateur utilisateur;

    private Categorie categorie;
    
    private Enchere enchere;
    
    private Retrait retrait;

    public ArticleVendu(int noArticle, String nom, String description, LocalDate beginAuction, LocalDate endAuction,
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
    
   

	public ArticleVendu(String nom, String description, LocalDate beginAuction, LocalDate endAuction, int initialPrice,
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

    public LocalDate getBeginAuction() {
        return beginAuction;
    }

    public void setBeginAuction(LocalDate beginAuction) {
        this.beginAuction = beginAuction;
    }

    public LocalDate getEndAuction() {
        return endAuction;
    }

    public void setEndAuction(LocalDate endAuction) {
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



	public Enchere getEnchere() {
		return enchere;
	}



	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}



	public Retrait getRetrait() {
		return retrait;
	}



	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}
}
