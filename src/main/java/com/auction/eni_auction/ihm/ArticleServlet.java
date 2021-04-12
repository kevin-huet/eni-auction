package com.auction.eni_auction.ihm;

import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "articleServlet", value = "/article/*")
public class ArticleServlet extends HttpServlet {




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String articleId = (request.getParameter("id"));
        ArticleVenduJdbc articleVenduJdbc = new ArticleVenduJdbc();
        ArticleVendu article = null;
        try {
           article = articleVenduJdbc.selectById(Integer.parseInt(articleId));
        } catch (DALException e) {
            e.printStackTrace();
        }
        request.setAttribute("derniereEnchere", article.getEnchere());
        request.setAttribute("article", article);
        System.out.println(article.getEnchere().getUtilisateur().getPseudo());
        this.getServletContext().getRequestDispatcher( "/article.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public void destroy() {

    }
}