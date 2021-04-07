package com.auction.eni_auction.dal.dao;

import com.auction.eni_auction.dal.DALException;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    T insert(T var) throws DALException, SQLException;
    T selectById(int id) throws DALException;
    List<T> selectAll() throws DALException;
    void update(T var) throws DALException;
    void delete(int id) throws DALException;
}