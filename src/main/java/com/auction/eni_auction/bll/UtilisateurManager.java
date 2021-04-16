package com.auction.eni_auction.bll;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.DAOFactory;
import com.auction.eni_auction.dal.jdbc.RetraitJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.sql.SQLException;

public class UtilisateurManager {

    private static UtilisateurManager instance = null;

    public UtilisateurManager() {

    }

    public static UtilisateurManager getInstance() {
        if(instance == null) {
            instance = new UtilisateurManager();
        }
        return instance;
    }

    public void addUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codepostal, String ville, String password) throws BusinessException {
        BusinessException be = new BusinessException();

        boolean incomplete = false;
        if (nom == null || nom.equals("")) {
            incomplete = true;
        }
        if (pseudo == null || pseudo.equals("")) {
            incomplete = true;
        } else if (!pseudo.matches("[A-Za-z0-9]+")) {
            be.addError("Le pseudo ne doit contenir que des caractÃ¨res alphanumÃ©rique.");
        }
        if (prenom == null || prenom.equals("")) {
            incomplete = true;
        }
        if (email == null || email.equals("")) {
            incomplete = true;
        } else if (!email.matches("^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            be.addError("Le format de l'email est incorrect.");
        }
        if (password == null || password.equals("")) {
            incomplete = true;
        }
        if (codepostal == null || codepostal.equals("")) {
            incomplete = true;
        }
        if (telephone == null || nom.equals("")) {
            incomplete = true;
        } else if (!telephone.trim().matches("[0-9]{2}(|-|.| )[0-9]{2}(|-|.| )[0-9]{2}(|-|.| )[0-9]{2}(|-|.| )[0-9]{2}")) {
            be.addError("Le format du tÃ©lÃ©phone est incorrect.");
        }
        if (rue == null || rue.equals("")) {
            incomplete = true;
        }
        if (ville == null || ville.equals("")) {
            incomplete = true;
        }
        if (incomplete) {
            be.addError("Tout les champs sont obligatoires.");
        }

        if (this.checkIfUserExist(pseudo, email)) {
            be.addError("Email ou Pseudo dÃ©jÃ  existant.");
        }

        if (be.hasErrors()) {
            throw be;
        }

        String phone1 = telephone.trim().replace(" ", "");
        String phone2 = phone1.replace(".", "");
        String phone = phone2.replace("-", "");

        Utilisateur user = new Utilisateur(pseudo, nom, prenom, email, phone, rue, codepostal, ville, password, 0, false);

        try {
            DAOFactory.getUtilisateurDAO().insert(user);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
            be.addError("Une erreur c'est produite lors de l'enregistrement.");
            throw be;
        }
    }

    public Utilisateur deleteUtilisateur(Utilisateur utilisateur) {
        try {
            DAOFactory.getUtilisateurDAO().delete(utilisateur.getNoUtilisateur());
            return utilisateur;
        } catch (DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur getUtilisateur(String idUser) {
        try {
            return DAOFactory.getUtilisateurDAO().selectById(Integer.parseInt(idUser));
        } catch (NumberFormatException | DALException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Utilisateur user, String nom, String prenom, String pseudo, String email,
                       String password, String codepostal, String telephone, String rue, String ville) throws BusinessException {

        BusinessException be = new BusinessException();

        boolean incomplete = false;
        if (nom == null || nom.equals("")) {
            incomplete = true;
        }
        if (pseudo == null || pseudo.equals("")) {
            incomplete = true;
        } else if (!pseudo.matches("[A-Za-z0-9]+")) {
            be.addError("Le pseudo ne doit contenir que des caractÃ¨res alphanumÃ©rique.");
        }
        if (prenom == null || prenom.equals("")) {
            incomplete = true;
        }
        if (email == null || email.equals("")) {
            incomplete = true;
        } else if (!email.matches("^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            be.addError("Le format de l'email est incorrect.");
        }
        if (password == null || password.equals("")) {
            incomplete = true;
        }
        if (codepostal == null || codepostal.equals("")) {
            incomplete = true;
        }
        if (telephone == null || nom.equals("")) {
            incomplete = true;
        } else if (!telephone.trim().matches("[0-9]{2}(|-|.| )[0-9]{2}(|-|.| )[0-9]{2}(|-|.| )[0-9]{2}(|-|.| )[0-9]{2}")) {
            be.addError("Le format du tÃ©lÃ©phone est incorrect.");
        }
        if (rue == null || rue.equals("")) {
            incomplete = true;
        }
        if (ville == null || ville.equals("")) {
            incomplete = true;
        }
        if (incomplete) {
            be.addError("Tout les champs sont obligatoires.");
        }

        try {
				if (DAOFactory.getUtilisateurDAO().checkForUniqueMail(email) || email.equals(user.getEmail())) {
					be.addError("Email déjà utilisé.");
				}
				if (DAOFactory.getUtilisateurDAO().checkForUniquePseudo(pseudo) || pseudo.equals(user.getPseudo())) {
					be.addError("Pseudo déjà utilisé.");
				}
			} catch (DALException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				be.addError("Une erreur c'est produite lors de la vérification des données.");
			}

        if (be.hasErrors()) {
            throw be;
        }

        String phone1 = telephone.trim().replace(" ", "");
        String phone2 = phone1.replace(".", "");
        String phone = phone2.replace("-", "");

        user.setNom(nom);
        user.setPrenom(prenom);
        user.setPseudo(pseudo);
        user.setEmail(email);
        user.setMotDePasse(password);
        user.setCodePostal(codepostal);
        user.setTelephone(telephone);
        user.setRue(rue);
        user.setVille(ville);

        try {
            DAOFactory.getUtilisateurDAO().update(user);
        } catch (DALException e) {
            e.printStackTrace();
            be.addError("Une erreur c'est produite lors de l'enregistrement.");
            throw be;
        }
    }

    public boolean checkIfUserExist(String pseudo, String email) {
        try {
            return DAOFactory.getUtilisateurDAO().checkForUniquePseudoAndMail(pseudo, email);
        } catch (DALException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Utilisateur getUserByCredentials(String email, String password) throws BusinessException {
        BusinessException be = new BusinessException();
        if ((email != null && !email.equals("")) && (password != null && !password.equals(""))) {
            try {
                Utilisateur user = DAOFactory.getUtilisateurDAO().selectUtilisateurByCredentials(email, password);
                if (user == null) {
                    be.addError("Mot de passe ou identifiant incorrect");
                    throw be;
                } else {
                    return user;
                }
            } catch (DALException e) {
                e.printStackTrace();
                be.addError("Une erreur c'est produite.");
                throw be;
            }
        } else {
            be.addError("Veuillez entrer un identifiant et un mot de passe");
            throw be;
        }
    }
}
