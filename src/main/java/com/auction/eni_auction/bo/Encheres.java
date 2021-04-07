package com.auction.eni_auction.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Encheres {

    public Encheres(Utilisateur utilisateur, ArticlesVendus article, int montantEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.montantEnchere = montantEnchere;
        dateEnchere =  LocalDateTime.now();
    }
    
    public Encheres(ArticlesVendus article, Utilisateur utilisateur, int montantEnchere, LocalDateTime dateEnchere) {
        this.article = article;
        this.utilisateur = utilisateur;
        this.montantEnchere = montantEnchere;
        this.dateEnchere =  dateEnchere;
    }

    private Utilisateur utilisateur;

    private ArticlesVendus article;

    private LocalDateTime dateEnchere;

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
