package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.util.List;

public interface ArticleVenduDAO extends DAO<ArticlesVendus> {
    List<ArticlesVendus> filterByCategory(Categorie categorie) throws DALException;
    List<ArticlesVendus> filterByString(String filter) throws DALException;
    List<Integer> filterByEtat(String etat) throws DALException;
    List<Integer> getArticlesFromASellerAndState (Utilisateur utilisateur, String state) throws DALException;
    void updateCurrentPrice(int noArticle, int newPrice) throws DALException;
}
