package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieManager {

    private static CategorieManager instance = null;

    public CategorieManager() {
    	
    }

    public static CategorieManager getInstance() {
        if(instance == null) {
            instance = new CategorieManager();
        }
        return instance;
    }


    public Categorie addCategorie(String name) {
        try {
            return DAOFactory.getCategorieDAO().insert(new Categorie(name));
        } catch (DALException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Categorie editCategorie(Categorie categorie) {
        return null;
    }

    public Categorie deleteCategorie(Categorie categorie) {
        return null;
    }

    public Categorie getCategorie(int parseInt) {
        try {
            return DAOFactory.getCategorieDAO().selectById(parseInt);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categorie> getAllCategories() {
        try {
            return DAOFactory.getCategorieDAO().selectAll();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<Categorie>();
    }
}
