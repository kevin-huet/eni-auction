package com.auction.eni_auction.servlets.auth;

import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "deleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        Utilisateur utilisateur = (session.getAttribute("user") != null) ? (Utilisateur) session.getAttribute("user") : null;
        session.setAttribute("user", null);

        assert utilisateur != null;
        try {
            utilisateurJdbc.delete(utilisateur.getNoUtilisateur());
        } catch (DALException e) {
            e.printStackTrace();
        }
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

}