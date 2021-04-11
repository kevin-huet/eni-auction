package com.auction.eni_auction.ihm;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Encheres;
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
    public static final String VUE = "/article.jsp";


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idAuction = (request.getParameter("id"));

        this.getServletContext().getRequestDispatcher( "/enchere.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String price = request.getParameter(PRICE);
        String articleId = request.getParameter(ARTICLE_ID);
        System.out.println(price+" "+articleId);
        ArticleVenduJdbc articleVenduJdbc = new ArticleVenduJdbc();
        EnchereJdbc enchereJdbc = new EnchereJdbc();
        ArticlesVendus article = null;
        try {
            article = articleVenduJdbc.selectById(Integer.parseInt(articleId));
        } catch (DALException e) {
            e.printStackTrace();
        }
        assert article != null;

        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        Utilisateur test = null;
        try {
            test = utilisateurJdbc.selectById(2);
        } catch (DALException e) {
            e.printStackTrace();
        }

        assert test != null;
        Encheres enchere = new Encheres(article.getNoArticle(), test.getNoUtilisateur(), Integer.parseInt(price), LocalDateTime.now());
        enchere.setUtilisateur(test);
        enchere.setArticle(article);
        try {
            if (enchereJdbc.selectById(article.getNoArticle(), test.getNoUtilisateur()) == null)
                enchereJdbc.insert(enchere);
            else
                enchereJdbc.update(enchere);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("article", article);
        request.setAttribute("sucess", "Votre enchère a bien été ajoutée");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    public void destroy() {

    }
}