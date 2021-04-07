package com.auction.eni_auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.auction.eni_auction.bo.Retraits;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.RetraitDAO;

public class RetraitJdbc implements RetraitDAO{
	
	private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?)";
	private static final String SELECT_ALL = "SELECT no_article, rue, code_postal, ville FROM RETRAITS";
	private static final String SELECT_ONE = "SELECT no_article, rue, code_postal, ville FROM RETRAITS WHERE no_article = ?";
	private static final String UPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private static final String DELETE = "DELETE FROM RETRAITS WHERE no_article = ?";
	
	@Override
	public Retraits insert(Retraits var) throws DALException, SQLException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setInt(1, var.getArticle().getNoArticle());
			pStmt.setString(2, var.getRue());
			pStmt.setString(3, var.getPostalCode());
			pStmt.setString(4, var.getCity());
			pStmt.executeUpdate();
			
			ResultSet rs = pStmt.getGeneratedKeys();
		} catch (SQLException e) {
			DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return var;
	}
	@Override
	public Retraits selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Retraits> selectAll() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(Retraits var) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
    		PreparedStatement pStmt = cnx.prepareStatement(UPDATE);
    		pStmt.setString(1, var.getRue());
			pStmt.setString(2, var.getPostalCode());
			pStmt.setString(3, var.getCity());
			pStmt.setInt(4, var.getArticle().getNoArticle());
			
			pStmt.executeQuery();
			
    	} catch (SQLException e) {
    		DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void delete(int id) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
    		PreparedStatement pStmt = cnx.prepareStatement(DELETE);
			pStmt.setInt(1, id);
			
			pStmt.executeQuery();
			
    	} catch (SQLException e) {
    		DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
