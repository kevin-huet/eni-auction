package com.auction.eni_auction.ihm;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "homeServlet", value = "/")
public class HomeServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArticleVenduJdbc articleJdbc = new ArticleVenduJdbc();
        List<ArticlesVendus> listArticles = new ArrayList<ArticlesVendus>();
        try {
            listArticles.add(articleJdbc.selectById(1));
        } catch (DALException e) {
            e.printStackTrace();
        }
        request.setAttribute("articles", listArticles);
        if (listArticles.isEmpty())
            request.setAttribute("test", "vide");
        else {
            request.setAttribute("test", "pas vide");
            request.setAttribute("objet", listArticles.get(0));
        }
        this.getServletContext().getRequestDispatcher( "/home.jsp" ).forward( request, response );
    }

    public void destroy() {

    }
}