package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface EnchereDAO extends DAO<Encheres> {
    List<Integer> getNoArticlesByUtilisateurAndEtat(Utilisateur utilisateur, String state) throws DALException, SQLException;
    List<Integer> getNoArticlesWonByUtilisateur(Utilisateur utilisateur) throws DALException, SQLException;
    HashMap<Integer, Integer> getAmountAndPseudoOfBestOffer(ArticlesVendus articleVendu) throws DALException, SQLException;
}