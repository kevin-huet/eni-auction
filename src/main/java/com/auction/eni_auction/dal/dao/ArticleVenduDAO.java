package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Retrait;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.util.List;

public interface ArticleVenduDAO extends DAO<ArticleVendu> {
    List<ArticleVendu> filterBase(String name, int categorieId) throws DALException;
    List<ArticleVendu> filterBuy(String name, int categorieId, String state, Utilisateur utilisateur) throws DALException;
    List<ArticleVendu> filterSell(String name, int categorieId, String state, Utilisateur utilisateur) throws DALException;
    int getCurrentHighest(int articleId) throws DALException;
}
