package com.auction.eni_auction.servlets;

import com.auction.eni_auction.bll.ArticleVenduManager;
import com.auction.eni_auction.bll.CategorieManager;
import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "homeServlet", value = "/")
public class HomeServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<ArticleVendu> listArticles;
        List<Categorie> listCategories ;
        Utilisateur user = null;
        HttpSession session = request.getSession();
        String cat = request.getParameter("cat");
        String search = request.getParameter("search");
        System.out.println(cat+ " " + search);
        if (session.getAttribute("user") != null)
            user = (Utilisateur) session.getAttribute("user");
        if (user == null)
            listArticles = ArticleVenduManager.getInstance().getBaseArticle(cat, search);
        else
            listArticles = ArticleVenduManager.getInstance().getBuyArticle(cat, search, user);
        listCategories = CategorieManager.getInstance().getAllCategories();
        request.setAttribute("articles", listArticles);
        request.setAttribute("categories", listCategories);
        this.getServletContext().getRequestDispatcher( "/home.jsp" ).forward( request, response );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cat = request.getParameter("cat");
        String search = request.getParameter("search");
        response.sendRedirect( request.getContextPath() + "/?cat="+cat+"&search="+search);
    }

    public void destroy() {

    }
}