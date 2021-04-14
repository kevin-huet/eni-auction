package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.sql.SQLException;

public class UtilisateurManager {

    private static UtilisateurManager instance = null;

    public UtilisateurManager() {

    }

    public static UtilisateurManager getInstance() {
        if(instance == null) {
            instance = new UtilisateurManager();
        }
        return instance;
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        try {
            return DAOFactory.getUtilisateurDAO().insert(utilisateur);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur editUtilisateur(Utilisateur utilisateur) {
        try {
        	DAOFactory.getUtilisateurDAO().update(utilisateur);
            return utilisateur;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur deleteUtilisateur(Utilisateur utilisateur) {
        try {
        	DAOFactory.getUtilisateurDAO().delete(utilisateur.getNoUtilisateur());
            return utilisateur;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur getUtilisateur(String idUser) {
        try {
            return DAOFactory.getUtilisateurDAO().selectById(Integer.parseInt(idUser));
        } catch (NumberFormatException | DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Utilisateur user) {
        try {
        	DAOFactory.getUtilisateurDAO().update(user);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUserExist(String pseudo, String email) {
        try {
            return DAOFactory.getUtilisateurDAO().checkForUniquePseudoAndMail(pseudo, email);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Utilisateur getUserByCredentials(String email, String password) {
        try {
            return DAOFactory.getUtilisateurDAO().selectUtilisateurByCredentials(email, password);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }
}
