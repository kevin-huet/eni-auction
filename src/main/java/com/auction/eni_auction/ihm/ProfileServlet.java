package com.auction.eni_auction.ihm;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "profileServlet", value = "/profile/*")
public class ProfileServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idUser = (request.getParameter("id"));
        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        Utilisateur user = null;

        try {
            user = utilisateurJdbc.selectById(Integer.parseInt(idUser));
        } catch (DALException e) {
            e.printStackTrace();
        }
        if (user == null) {

        } else if (user != null && user == request.getSession().getAttribute("user"))
            request.setAttribute("user", user);
        this.getServletContext().getRequestDispatcher( "/profile.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        String nom = request.getParameter("nom");
        String pseudo = request.getParameter("pseudo");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String codepostal = request.getParameter("codepostal");
        String telephone = request.getParameter("telephone");
        String rue = request.getParameter("rue");
        String ville = request.getParameter("ville");
        String id = request.getParameter("id");
        Utilisateur user = null;
        HttpSession session = request.getSession();
        try {
            if (session.getAttribute("user") != null)
                user = (Utilisateur) session.getAttribute("user");
            if (user != null) {
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setPseudo(pseudo);
                user.setEmail(email);
                user.setMotDePasse(password);
                user.setCodePostal(codepostal);
                user.setTelephone(telephone);
                user.setRue(rue);
                user.setVille(ville);
                utilisateurJdbc.update(user);

            }
        } catch (DALException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath()+"/profile?alert=success&id="+id);
    }
}
