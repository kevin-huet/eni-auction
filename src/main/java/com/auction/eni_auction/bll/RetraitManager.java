package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Retrait;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;

import java.sql.SQLException;

public class RetraitManager {

    private static RetraitManager instance = null;

    public RetraitManager() {

    }

    public static RetraitManager getInstance() {
        if(instance == null) {
            instance = new RetraitManager();
        }
        return instance;
    }

    public Retrait addRetrait(Retrait retrait) {
        try {
            return DAOFactory.getRetraitDAO().insert(retrait);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Retrait editRetrait(Retrait retrait) {
        try {
        	DAOFactory.getRetraitDAO().update(retrait);
            return retrait;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Retrait deleteRetrait(Retrait retrait) {
        try {
        	DAOFactory.getRetraitDAO().delete(retrait.getArticle().getNoArticle());
            return retrait;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }
}
