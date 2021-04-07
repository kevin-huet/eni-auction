package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.ErrorCodes;
import com.auction.eni_auction.dal.dao.EnchereDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchereJdbc implements EnchereDAO {

    @Override
    public Encheres insert(Encheres var) throws DALException, SQLException {
		return var;
        
    }

    @Override
    public Encheres selectById(int id) throws DALException {
        return null;
    }

    @Override
    public List<Encheres> selectAll() throws DALException {
        return null;
    }

    @Override
    public void update(Encheres var) throws DALException {

    }

    @Override
    public void delete(int id) throws DALException {

    }

    @Override
    public List<Integer> getNoArticlesByUtilisateurAndEtat(Utilisateur utilisateur, String state) throws DALException, SQLException {
        return null;
    }

    @Override
    public List<Integer> getNoArticlesWonByUtilisateur(Utilisateur utilisateur) throws DALException, SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, Integer> getAmountAndPseudoOfBestOffer(ArticlesVendus articleVendu) throws DALException, SQLException {
        return null;
    }
}