package com.auction.eni_auction.servlets;

import com.auction.eni_auction.bll.ArticleVenduManager;
import com.auction.eni_auction.bll.CategorieManager;
import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "articleCreationServlet", value = "/article/create")
public class ArticleCreationServlet extends HttpServlet {




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Categorie> categories = new ArrayList<>();
        String alert = request.getParameter("alert");

        if (alert != null && alert.equals("error")) {
            request.setAttribute("error", "Vous devez être connecté pour créer un article");
        }
        categories = CategorieManager.getInstance().getAllCategories();
        request.setAttribute("categories", categories);
        this.getServletContext().getRequestDispatcher( "/article.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (request.getSession().getAttribute("user") != null) ? (Utilisateur) request.getSession().getAttribute("user") : null;
        String name = request.getParameter("nom");
        String desc = request.getParameter("desc");
        String date = request.getParameter("date");
        String price = request.getParameter("price");
        String category = request.getParameter("cat");
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ArticleVendu article = null;

        if (utilisateur != null) {
            article = ArticleVenduManager.getInstance().addArticle(category, name, desc, localDate, price, 0, utilisateur);
            assert article != null;
            response.sendRedirect(request.getContextPath() + "/article?id="+article.getNoArticle());
        } else {
            response.sendRedirect(request.getContextPath() + "/article/create?alert=error");
        }

    }
    public void destroy() {

    }
}