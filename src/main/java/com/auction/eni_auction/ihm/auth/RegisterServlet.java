package com.auction.eni_auction.ihm.auth;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private String message;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher( "/register.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String firstName = request.getParameter("firstName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String adresse = request.getParameter("adresse");
        String codepostal = request.getParameter("codepostal");

        this.getServletContext().getRequestDispatcher("/WEB-INF/food.jsp").forward(request, response);
    }

    public void destroy() {
    }
}