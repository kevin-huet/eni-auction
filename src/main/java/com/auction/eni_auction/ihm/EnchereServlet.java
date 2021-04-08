package com.auction.eni_auction.ihm;

import com.auction.eni_auction.bo.Encheres;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "enchere", value = "/enchere/*")
public class EnchereServlet extends HttpServlet {
    private String message;



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idAuction = (request.getParameter("id"));
        HttpSession session = request.getSession();
        //Encheres encheres = new Encheres();
        request.setAttribute("test", idAuction);
        this.getServletContext().getRequestDispatcher( "/enchere.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String price = request.getParameter("price");
        String article = request.getParameter("article");
        String enchere = request.getParameter("enchere");

    }
    public void destroy() {

    }
}