package com.auction.eni_auction.servlets;

import com.auction.eni_auction.bll.ArticleVenduManager;
import com.auction.eni_auction.bo.ArticleVendu;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "articleServlet", value = "/article/*")
public class ArticleServlet extends HttpServlet {




    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String articleId = (request.getParameter("id"));
        String alert = request.getParameter("alert");
        ArticleVendu article = ArticleVenduManager.getInstance().getArticle(articleId);

        if (alert != null && alert.equals("error_user")) {
            request.setAttribute("error", "Vous devez être connecté pour effectuer une enchère");
        } else if (alert != null && alert.equals("success")) {
            request.setAttribute("success", "Votre enchère à bien été prise en compte");
        } else if (alert != null) {
            request.setAttribute("error", alert);
        }
        request.setAttribute("derniereEnchere", article.getEnchere());
        request.setAttribute("article", article);
        this.getServletContext().getRequestDispatcher( "/show_article.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public void destroy() {

    }
}