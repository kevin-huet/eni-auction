package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface EnchereDAO {
    Enchere insert(Enchere var) throws DALException, SQLException;
    Enchere selectById(int articleId, int utilisateurId) throws DALException;
    void update(Enchere var) throws DALException;
    void delete(int articleId, int utilisateurId) throws DALException;
}