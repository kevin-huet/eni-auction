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
        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        Utilisateur user = null;
        try {
            user = utilisateurJdbc.selectById(1);
        } catch (DALException e) {
            e.printStackTrace();
        }
        try {
            listArticles = articleJdbc.filterSell("", 0, "ouvert", user);
        } catch (DALException e) {
            e.printStackTrace();
        }
        for (ArticlesVendus v:listArticles) {
            System.out.println(v.getNoArticle());
        }
        request.setAttribute("articles", listArticles);

        this.getServletContext().getRequestDispatcher( "/home.jsp" ).forward( request, response );
    }

    public void destroy() {

    }
}