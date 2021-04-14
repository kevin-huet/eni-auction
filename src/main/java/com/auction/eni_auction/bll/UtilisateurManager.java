package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.sql.SQLException;

public class UtilisateurManager {

    private static UtilisateurJdbc utilisateurJdbc = null;
    private static UtilisateurManager instance = null;

    public UtilisateurManager() {
        utilisateurJdbc =  new UtilisateurJdbc();
    }

    public static UtilisateurManager getInstance() {
        if(instance == null) {
            instance = new UtilisateurManager();
        }
        return instance;
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        try {
            return utilisateurJdbc.insert(utilisateur);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur editUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurJdbc.update(utilisateur);
            return utilisateur;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur deleteUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurJdbc.delete(utilisateur.getNoUtilisateur());
            return utilisateur;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur getUtilisateur(String idUser) {
        try {
            return utilisateurJdbc.selectById(Integer.parseInt(idUser));
        } catch (NumberFormatException | DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Utilisateur user) {
        try {
            utilisateurJdbc.update(user);
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
