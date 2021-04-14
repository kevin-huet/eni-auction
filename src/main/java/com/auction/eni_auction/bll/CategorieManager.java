package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieManager {

    private static CategorieJdbc categorieJdbc = null;
    private static CategorieManager instance = null;

    public CategorieManager() {
        categorieJdbc =  new CategorieJdbc();
    }

    public static CategorieManager getInstance() {
        if(instance == null) {
            instance = new CategorieManager();
        }
        return instance;
    }


    public Categorie addCategorie(String name) {
        try {
            return categorieJdbc.insert(new Categorie(name));
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
            return categorieJdbc.selectById(parseInt);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categorie> getAllCategories() {
        try {
            return categorieJdbc.selectAll();
        } catch (DALException e) {
            e.printStackTrace();
        }
        return new ArrayList<Categorie>();
    }
}
