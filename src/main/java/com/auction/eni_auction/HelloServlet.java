package com.auction.eni_auction;

import com.auction.eni_auction.bo.ArticlesVendus;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.ArticleVenduJdbc;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        ArticleVenduJdbc articleJdbc = new ArticleVenduJdbc();
        UtilisateurJdbc utilisateurJdbc = new UtilisateurJdbc();
        Utilisateur user = null;

        CategorieJdbc categorieJdbc = new CategorieJdbc();
        Categorie cat = new Categorie("test");
        try {
            categorieJdbc.insert(cat);
        } catch (DALException | SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }

        try {
            user = utilisateurJdbc.selectById(1);
        } catch (DALException e) {
            e.printStackTrace();
        }
        Categorie testcat = null;
        try {
            testcat = categorieJdbc.selectById(1);
        } catch (DALException e) {
            e.printStackTrace();
        }
        ArticlesVendus test = new ArticlesVendus("article2", "ceci est l'article 2",
                LocalDate.now(), (LocalDate.now()).plusMonths(1), 100,
        200, user, testcat);
        try {
            if (testcat != null && user != null)
                articleJdbc.insert(test);
        } catch (DALException | SQLException e) {
            e.printStackTrace();
        }
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    	
    }
}