package com.auction.eni_auction.ihm;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "categorieServlet", value = "/category/*")
public class CategorieServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CategorieJdbc categorieJdbc = new CategorieJdbc();
        List<Categorie> categories = new ArrayList<>();
        try {
            categories = categorieJdbc.selectAll();

        } catch (DALException e) {
            e.printStackTrace();
        }
        request.setAttribute("categories", categories);
        this.getServletContext().getRequestDispatcher( "/category.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nom");
        CategorieJdbc categorieJdbc = new CategorieJdbc();
        try {
            categorieJdbc.insert(new Categorie(name));
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("alert", "success");
        response.sendRedirect( request.getContextPath() + "/category");
    }

}
