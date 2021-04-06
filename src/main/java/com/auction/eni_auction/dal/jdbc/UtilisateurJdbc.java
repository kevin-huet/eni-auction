package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.ConnectionProvider;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.dao.UtilisateurDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			pStmt.setString(4, var.getTelephone());
			pStmt.setString(5, var.getRue());
			pStmt.setString(6, var.getCodePostal());
			pStmt.setString(7, var.getVille());
			pStmt.setString(8, var.getMotDePasse());
			pStmt.setInt(9, var.getCredit());
			pStmt.setBoolean(10, var.isAdministrateur());
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
        return null;
    }

    @Override
    public List<Utilisateur> selectAll() throws DALException {
        return null;
    }

    @Override
    public void update(Utilisateur var) throws DALException {

    }

    @Override
    public void delete(Utilisateur var) throws DALException {

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
    public Utilisateur updateCredit(int noUtilisateur, int newCredit) throws DALException {
		return null;

    }
}
