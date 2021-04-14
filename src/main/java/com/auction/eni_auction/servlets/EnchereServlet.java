package com.auction.eni_auction.servlets;

import com.auction.eni_auction.bll.ArticleVenduManager;
import com.auction.eni_auction.bll.EnchereManager;
import com.auction.eni_auction.bo.ArticleVendu;
import com.auction.eni_auction.bo.Enchere;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.EnchereJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "enchere", value = "/enchere/*")
public class EnchereServlet extends HttpServlet {
    private String message;
    public static final String PRICE = "price";
    public static final String ARTICLE_ID = "articleId";
    public static final String VUE = "/show_article.jsp";


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idAuction = (request.getParameter("id"));

        this.getServletContext().getRequestDispatcher( "/enchere.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String price = request.getParameter(PRICE);
        String articleId = request.getParameter(ARTICLE_ID);
        System.out.println(price+" "+articleId);
        ArticleVendu article = null;
        Utilisateur user = (request.getSession().getAttribute("user") != null) ? (Utilisateur) request.getSession().getAttribute("user") : null;
        Enchere enchere = null;

        if (user == null) {
            response.sendRedirect( request.getContextPath() + "/article?alert=error_user&id="+articleId);
        } else if (user.getCredit() >= Integer.parseInt(price)) {
            article = ArticleVenduManager.getInstance().getArticle(articleId);
            enchere = new Enchere(article, user, Integer.parseInt(price), LocalDateTime.now());
            enchere.setUtilisateur(user);
            enchere.setArticle(article);
            if (EnchereManager.getInstance().getEnchere(article.getNoArticle(), user.getNoUtilisateur()) == null)
                EnchereManager.getInstance().addEnchere(enchere);
            else
                EnchereManager.getInstance().update(enchere);
            request.setAttribute("success", "Votre enchère a bien été ajoutée");
            response.sendRedirect(request.getContextPath() + "/article?alert=success&id=" + articleId);
        } else
            response.sendRedirect( request.getContextPath() + "/article?alert=error_credit&id="+articleId);
    }
    public void destroy() {

    }
}