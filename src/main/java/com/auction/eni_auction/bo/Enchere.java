package com.auction.eni_auction.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Enchere {

    public Enchere(Utilisateur utilisateur, ArticleVendu article, int montantEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.montantEnchere = montantEnchere;
        dateEnchere =  LocalDateTime.now();

    }

    public Enchere(ArticleVendu article, Utilisateur utilisateur, int montantEnchere, LocalDateTime dateEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.montantEnchere = montantEnchere;
        this.dateEnchere =  dateEnchere;

    }

    private Utilisateur utilisateur;

    private ArticleVendu article;

    private LocalDateTime dateEnchere;

    private int montantEnchere;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Utilisateur getutilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ArticleVendu getArticle() {
        return article;
    }

    public void setArticle(ArticleVendu article) {
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