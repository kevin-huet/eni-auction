package com.auction.eni_auction.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Encheres {

    public Encheres(int no_article, int no_utilisateur, Utilisateur utilisateur, ArticlesVendus article, int montantEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.montantEnchere = montantEnchere;
        dateEnchere =  LocalDateTime.now();
        this.no_article = no_article;
        this.no_utilisateur = no_utilisateur;
    }
    
    public Encheres(int no_article, int no_utilisateur, ArticlesVendus article, Utilisateur utilisateur, int montantEnchere, LocalDateTime dateEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.montantEnchere = montantEnchere;
        this.dateEnchere =  dateEnchere;
        this.no_article = no_article;
        this.no_utilisateur = no_utilisateur;
    }
    
    // utiliser ce constructeur
	public Encheres(int no_article, int no_utilisateur, int montantEnchere, LocalDateTime dateEnchere) {
    	this.no_article = no_article;
        this.no_utilisateur = no_utilisateur;
        this.montantEnchere = montantEnchere;
        this.dateEnchere =  dateEnchere;
    }

    private Utilisateur utilisateur;

    private ArticlesVendus article;

    private LocalDateTime dateEnchere;

    private int montantEnchere;
    
    private int no_utilisateur;
    
    private int no_article;
    
    public int getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(int no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

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

    public LocalDateTime getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDateTime dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }
}
