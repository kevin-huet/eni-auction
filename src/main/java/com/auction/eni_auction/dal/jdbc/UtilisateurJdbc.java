package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.UtilisateurDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UtilisateurJdbc implements UtilisateurDAO {
	
	private static final String SELECT_ALL = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS";
	private static final String SELECT_ID = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = ?";
	private static final String INSERT_ONE = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_ONE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, administrateur = ?  WHERE no_utilisateur = ?";
    private static final String DELETE_ONE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
    private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
    
	@Override
    public Utilisateur insert(Utilisateur var) throws DALException, SQLException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(INSERT_ONE, PreparedStatement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, var.getPseudo());
			pStmt.setString(2, var.getNom());
			pStmt.setString(3, var.getPrenom());
			pStmt.setString(4, var.getEmail());
			pStmt.setString(5, var.getTelephone());
			pStmt.setString(6, var.getRue());
			pStmt.setString(7, var.getCodePostal());
			pStmt.setString(8, var.getVille());
			pStmt.setString(9, var.getMotDePasse());
			pStmt.setInt(10, var.getCredit());
			pStmt.setBoolean(11, var.isAdministrateur());
			pStmt.executeUpdate();
			
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				var.setNoUtilisateur(rs.getInt(1));
			}
		} catch (SQLException e) {
			DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
		return var;
    }

    @Override
    public Utilisateur selectById(int id) throws DALException {
    	Utilisateur utilisateur = null;
    	try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(SELECT_ID);
        	pStmt.setInt(1, id);
        	ResultSet rs = pStmt.executeQuery();
        	
        	while (rs.next()) {
        		utilisateur = new Utilisateur(
        				rs.getInt("no_utilisateur"),
        				rs.getString("pseudo"),
        				rs.getString("nom"),
        				rs.getString("prenom"),
        				rs.getString("email"),
        				rs.getString("telephone"),
        				rs.getString("rue"),
        				rs.getString("code_postal"),
        				rs.getString("ville"),
        				rs.getString("mot_de_passe"),
        				rs.getInt("credit"),
        				rs.getBoolean("administrateur")
        			);
        	}
        } catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
		return utilisateur;
    }

    @Override
    public List<Utilisateur> selectAll() throws DALException {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
        	PreparedStatement pStmt = cnx.prepareStatement(SELECT_ALL);
        	ResultSet rs = pStmt.executeQuery();
        	
        	while (rs.next()) {
        		Utilisateur utilisateur = new Utilisateur(
        				rs.getInt("no_utilisateur"),
        				rs.getString("pseudo"),
        				rs.getString("nom"),
        				rs.getString("prenom"),
        				rs.getString("email"),
        				rs.getString("telephone"),
        				rs.getString("rue"),
        				rs.getString("code_postal"),
        				rs.getString("ville"),
        				rs.getString("mot_de_passe"),
        				rs.getInt("credit"),
        				rs.getBoolean("administrateur")
        			);
        		utilisateurs.add(utilisateur);
        	}
        } catch (SQLException e) {
        	DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
        return utilisateurs;
    }

    @Override
    public void update(Utilisateur var) throws DALException {
    	try (Connection cnx = ConnectionProvider.getConnection()) {
    		PreparedStatement pStmt = cnx.prepareStatement(UPDATE_ONE);
    		pStmt.setString(1, var.getPseudo());
			pStmt.setString(2, var.getNom());
			pStmt.setString(3, var.getPrenom());
			pStmt.setString(4, var.getEmail());
			pStmt.setString(5, var.getTelephone());
			pStmt.setString(6, var.getRue());
			pStmt.setString(7, var.getCodePostal());
			pStmt.setString(8, var.getVille());
			pStmt.setString(9, var.getMotDePasse());
			pStmt.setBoolean(11, var.isAdministrateur());
			
			pStmt.executeQuery();
			
    	} catch (SQLException e) {
    		DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
    }

    @Override
    public void delete(Utilisateur var) throws DALException {
    	try (Connection cnx = ConnectionProvider.getConnection()) {
    		PreparedStatement pStmt = cnx.prepareStatement(DELETE_ONE);
    		pStmt.setInt(1, var.getNoUtilisateur());
    		
			pStmt.executeQuery();
    	} catch (SQLException e) {
    		DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}
    }

    @Override
    public boolean checkForUniquePseudoAndMail(String pseudo, String mail) throws DALException {
        return false;
    }

    @Override
    public boolean checkForUniquePseudo(String pseudo) throws DALException {
        return false;
    }

    @Override
    public boolean checkForUniqueMail(String mail) throws DALException {
        return false;
    }

    @Override
    public Utilisateur selectUtilisateurByPseudo(String pseudo) throws DALException {
        return null;
    }

    @Override
    public HashMap<Integer, String> selectUtilisateursWithCurrentAuction() throws DALException {
        return null;
    }

    @Override
    public void updateCredit(Utilisateur utilisateur, int newCredit) throws DALException {
    	try (Connection cnx = ConnectionProvider.getConnection()) {
    		PreparedStatement pStmt = cnx.prepareStatement(UPDATE_CREDIT);
    		pStmt.setInt(1, newCredit);
    		pStmt.setInt(2, utilisateur.getNoUtilisateur());
    		
			pStmt.executeQuery();
    	} catch (SQLException e) {
    		DALException exception = new DALException();
			exception.addError(e.getErrorCode());
			throw exception;
		}

    }
}
