package com.auction.eni_auction.servlets;

import com.auction.eni_auction.bll.CategorieManager;
import com.auction.eni_auction.bo.Categorie;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.CategorieJdbc;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "categorieServlet", value = "/category/*")
public class CategorieServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Categorie> categories = new ArrayList<>();
        HttpSession session = request.getSession();
        Utilisateur user = null;

        if (session.getAttribute("user") != null) {
            user = (Utilisateur) session.getAttribute("user");
            if (user != null && user.isAdministrateur()) {
                categories = CategorieManager.getInstance().getAllCategories();
                request.setAttribute("categories", categories);
                this.getServletContext().getRequestDispatcher("/category.jsp").forward(request, response);
            }
        } else
            response.sendRedirect( request.getContextPath() + "/");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nom");
        HttpSession session = request.getSession();
        Utilisateur user = null;

        if (session.getAttribute("user") != null) {
            user = (Utilisateur) session.getAttribute("user");
            if (user != null && user.isAdministrateur()) {

                request.setAttribute("alert", "success");
                response.sendRedirect(request.getContextPath() + "/category");
            }
        } else
            response.sendRedirect(request.getContextPath() + "/");
    }
}
