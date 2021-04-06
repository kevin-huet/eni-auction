package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.UtilisateurDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class UtilisateurJdbc implements UtilisateurDAO {

    @Override
    public void insert(Utilisateur var) throws DALException, SQLException {

    }

    @Override
    public Utilisateur selectById(int id) throws DALException {
        return null;
    }

    @Override
    public List<Utilisateur> selectAll() throws DALException {
        return null;
    }

    @Override
    public void update(Utilisateur var) throws DALException {

    }

    @Override
    public void delete(Utilisateur var) throws DALException {

    }

    @Override
    public boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException {
        return false;
    }

    @Override
    public boolean checkForUniquePseudo(String pseudo) throws DALException {
        return false;
    }

    @Override
    public boolean checkForUniqueMail(String mail) throws DALException {
        return false;
    }

    @Override
    public Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException {
        return null;
    }

    @Override
    public HashMap<Integer, String> selectUtilisateursWithCurrentAuction() throws DALException {
        return null;
    }

    @Override
    public void updateCredit(int noUtilisateur, int newCredit) throws DALException {

    }
}
