package com.auction.eni_auction.servlets.auth;

import com.auction.eni_auction.bll.UtilisateurManager;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String alert = request.getParameter("alert");

        if (alert != null && !alert.equals("")) {
            request.setAttribute("error", "Email ou Pseudo déjà existant");
        }
        this.getServletContext().getRequestDispatcher( "/register.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String pseudo = request.getParameter("pseudo");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String codepostal = request.getParameter("codepostal");
        String telephone = request.getParameter("telephone");
        String rue = request.getParameter("rue");
        String ville = request.getParameter("ville");
        Utilisateur user = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, password, 0, false);

        if (!UtilisateurManager.getInstance().checkIfUserExist(pseudo, email)) {
            UtilisateurManager.getInstance().addUtilisateur(user);
            response.sendRedirect( request.getContextPath() + "/login?alert=register");
        } else
            response.sendRedirect( request.getContextPath() + "/register?alert=alreadyExist");
    }

    public void destroy() {
    }


}