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

        } else
            request.setAttribute("user", user);
        this.getServletContext().getRequestDispatcher( "/profile.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
