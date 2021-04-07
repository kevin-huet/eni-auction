package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Retraits;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.util.List;

public interface ArticleVenduDAO extends DAO<ArticlesVendus> {
    List<ArticlesVendus> filterBase(String name, int categorieId) throws DALException;
    List<ArticlesVendus> filterBuy(String name, int categorieId, String state, Utilisateur utilisateur) throws DALException;
    List<ArticlesVendus> filterSell(String name, int categorieId, String state, Utilisateur utilisateur) throws DALException;
    
    void AddEnchere(int articleId, Encheres enchere) throws DALException;
    int getCurrentHighest(int articleId) throws DALException;
    void addRetrait(int articleId, Retraits retrait) throws DALException;
}
