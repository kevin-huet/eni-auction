package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface EnchereDAO {
    Encheres insert(Encheres var) throws DALException, SQLException;
    Encheres selectById(int articleId, int utilisateurId) throws DALException;
    void update(Encheres var) throws DALException;
    void delete(int articleId, int utilisateurId) throws DALException;
}