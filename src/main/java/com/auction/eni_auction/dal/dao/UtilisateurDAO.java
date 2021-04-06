package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;

import java.util.HashMap;

public interface UtilisateurDAO extends DAO<Utilisateur> {
    boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException;

    boolean checkForUniquePseudo(String pseudo) throws DALException;

    boolean checkForUniqueMail(String mail) throws DALException;

    Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException;

    HashMap<Integer, String> selectUtilisateursWithCurrentAuction() throws DALException;

    void updateCredit(Utilisateur utilisateur, int newCredit) throws DALException;
}
