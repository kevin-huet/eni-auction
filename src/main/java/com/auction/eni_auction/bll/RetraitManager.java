package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Retrait;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;

import java.sql.SQLException;

public class RetraitManager {

    private static RetraitJdbc retraitJdbc = null;
    private static RetraitManager instance = null;

    public RetraitManager() {
        retraitJdbc =  new RetraitJdbc();
    }

    public static RetraitManager getInstance() {
        if(instance == null) {
            instance = new RetraitManager();
        }
        return instance;
    }

    public Retrait addRetrait(Retrait retrait) {
        try {
            return retraitJdbc.insert(retrait);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Retrait editRetrait(Retrait retrait) {
        try {
            retraitJdbc.update(retrait);
            return retrait;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Retrait deleteRetrait(Retrait retrait) {
        try {
            retraitJdbc.delete(retrait.getArticle().getNoArticle());
            return retrait;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }
}
