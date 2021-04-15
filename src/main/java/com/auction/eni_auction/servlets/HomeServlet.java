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
        String status = request.getParameter("status");
        String sell = request.getParameter("sell");
        String buy = request.getParameter("buy");

        if (session.getAttribute("user") != null)
            user = (Utilisateur) session.getAttribute("user");
        if (user == null)
            listArticles = ArticleVenduManager.getInstance().getBaseArticle(cat, search);
        else if (buy != null && buy.equals("on"))
            listArticles = ArticleVenduManager.getInstance().getBuyArticle(cat, search, user, (status == null) ? "ouvert" : status);
        else if (sell != null && sell.equals("on"))
            listArticles = ArticleVenduManager.getInstance().getSellArticle(cat, search, user, (status == null) ? "ouvert" : status);
        else
            listArticles = ArticleVenduManager.getInstance().getBuyArticle(cat, search, user, "ouvert");
        listCategories = CategorieManager.getInstance().getAllCategories();
        request.setAttribute("articles", listArticles);
        request.setAttribute("categories", listCategories);
        this.getServletContext().getRequestDispatcher( "/home.jsp" ).forward( request, response );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cat = request.getParameter("cat");
        String search = request.getParameter("search");
        String sell = request.getParameter("sell");
        String buy = request.getParameter("buy");
        String openAuction = request.getParameter("open_auction");
        String actualAuction = request.getParameter("actual_auction");
        String winAuction = request.getParameter("win_auction");
        String mySales = request.getParameter("my_sales");
        String notStartedSales  = request.getParameter("not_started_sales");
        String salesOver  = request.getParameter("sales_over");
        String status;
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null && buy != null && !buy.equals("null")) {
            status = getStatusBuy(openAuction, actualAuction, winAuction);
            response.sendRedirect(request.getContextPath()+"/?cat="+cat+"&search="+search+"&buy="+buy+"&status="+status);
        } else if (session.getAttribute("user") != null && sell != null && !sell.equals("null")) {
            status = getStatusSell(mySales, notStartedSales, salesOver);
            response.sendRedirect( request.getContextPath() + "/?cat="+cat+"&search="+search+"&sell="+sell+"&status="+status);
        } else
            response.sendRedirect( request.getContextPath() + "/?cat="+cat+"&search="+search);
    }

    private String getStatusBuy(String openAuction, String actualAuction, String winAuction) {
        if (actualAuction != null && actualAuction.equals("on")) {
            return "en cours";
        } else if (openAuction != null && openAuction.equals("on")) {
            return "ouvert";
        } else if (winAuction != null && winAuction.equals("on")) {
            return "finis";
        }
        return "ouvert";
    }

    private String getStatusSell(String mySales, String notStartedSales, String salesOver) {
        if (mySales != null && mySales.equals("on")) {
            return "ouvert";
        } else if (notStartedSales != null && notStartedSales.equals("on")) {
            return "non débuté";
        } else if (salesOver != null && salesOver.equals("on")) {
            return "finis";
        }
        return "ouvert";
    }
}