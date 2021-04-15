package com.auction.eni_auction.servlets.auth;

import com.auction.eni_auction.bll.UtilisateurManager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "logoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.invalidate();
        response.sendRedirect( request.getContextPath() + "/");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

}