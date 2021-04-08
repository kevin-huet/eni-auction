package com.auction.eni_auction.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.CategorieDAO;

public class CategorieJdbc implements CategorieDAO {
	
	private static final String INSERT = "INSERT INTO CATEGORIES (libelle) VALUES (?)";
	private static final String SELECT_ALL = "SELECT no_categorie, libelle FROM CATEGORIES";
	private static final String SELECT_ONE = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = ?";
	private static final String UPDATE = "UPDATE CATEGORIES SET libelle = ? WHERE no_categorie = ?";
	private static final String DELETE = "DELETE FROM CATEGORIES WHERE no_categorie = ?";
	@Override
	public Categorie insert(Categorie var) throws DALException, SQLException {
		try (Connection cnx = JdbcTools.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, var.getLibelle());
			pStmt.executeUpdate();
			
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				var.setNoCategorie(rs.getInt(1));
			}
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
	public Categorie selectById(int id) throws DALException {
		Categorie categorie = null;
    	try (Connection cnx = JdbcTools.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(SELECT_ONE);
        	pStmt.setInt(1, id);
        	ResultSet rs = pStmt.executeQuery();
        	
        	while (rs.next()) {
        		categorie = new Categorie(
        				rs.getInt("no_categorie"),
        				rs.getString("libelle")
        			);
        	}
        } catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categorie;
	}

	@Override
	public List<Categorie> selectAll() throws DALException {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(SELECT_ALL);
        	ResultSet rs = pStmt.executeQuery();
        	
        	while (rs.next()) {
        		Categorie categorie = new Categorie(
        				rs.getInt("no_categorie"),
        				rs.getString("libelle")
        			);
        		categories.add(categorie);
        	}
        } catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return categories;
	}

	@Override
	public void update(Categorie var) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
    		PreparedStatement pStmt = cnx.prepareStatement(UPDATE);
    		pStmt.setString(1, var.getLibelle());
			pStmt.setInt(2, var.getNoCategorie());
			
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
