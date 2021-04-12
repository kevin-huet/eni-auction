package com.auction.eni_auction.ihm.auth;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private String message;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String alert = request.getParameter("alert");

        if (alert != null) {
            request.setAttribute("sucess", "Vous êtes bien inscrit, vous pouvez à présent vous connecter");
        }
        this.getServletContext().getRequestDispatcher( "/login.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        if (email != null && password != null) {
            Utilisateur user = null;
            try {
                user = utilisateurJdbc.selectUtilisateurByCredentials(email, password);
            } catch (DALException e) {
                e.printStackTrace();
            }
            if (user != null) {
                session.setAttribute("Pseudo", user.getPseudo());
                session.setAttribute("Nom", user.getNom());
                session.setAttribute("Prenom", user.getPrenom());
                session.setAttribute("Mail", user.getEmail());
                session.setAttribute("Telephone", user.getTelephone());
                session.setAttribute("Rue", user.getRue());
                session.setAttribute("Cp", user.getCodePostal());
                session.setAttribute("Ville", user.getVille());
                session.setAttribute("Credit", user.getCredit());
                session.setAttribute("numUtil", user.getNoUtilisateur());
                session.setAttribute("user", user);
            } else {
                System.out.println("login error");
                request.setAttribute("error", "Mot de passe ou identifiant incorrect");
                session.setAttribute("user", null);

                doGet(request, response);

            }
        } else {
            System.out.println("value null");
            session.setAttribute("sessionUtilisateur", null);
        }
        response.sendRedirect( request.getContextPath() + "/");
    }

    public void destroy() {
    }
}