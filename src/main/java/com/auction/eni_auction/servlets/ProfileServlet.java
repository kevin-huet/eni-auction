package com.auction.eni_auction.servlets;

import com.auction.eni_auction.bll.BusinessException;
import com.auction.eni_auction.bll.UtilisateurManager;
import com.auction.eni_auction.bo.Utilisateur;
import com.auction.eni_auction.dal.DALException;
import com.auction.eni_auction.dal.jdbc.UtilisateurJdbc;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "profileServlet", value = "/profile/*")
public class ProfileServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idUser = (request.getParameter("id"));
        Utilisateur user = UtilisateurManager.getInstance().getUtilisateur(idUser);

        if (user == null) {

        } else if (user != null && user == request.getSession().getAttribute("user"))
            request.setAttribute("user", user);
        this.getServletContext().getRequestDispatcher( "/profile.jsp" ).forward( request, response );

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String pseudo = request.getParameter("pseudo");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String codepostal = request.getParameter("codepostal");
        String telephone = request.getParameter("telephone");
        String rue = request.getParameter("rue");
        String ville = request.getParameter("ville");
        String id = request.getParameter("id");
        Utilisateur user = null;
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null)
            user = (Utilisateur) session.getAttribute("user");
        if (user != null && (user.getNoUtilisateur() == Integer.parseInt(id) || user.isAdministrateur()) ) {
            try {
                UtilisateurManager.getInstance().update(user, nom, prenom, pseudo, email, password, codepostal, telephone, rue, ville);
                response.sendRedirect(request.getContextPath()+"/profile?alert=success&id="+id);
            } catch (BusinessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                StringBuilder error = new StringBuilder();
                int size = e.getErrorList().size();
                for (int i = 0; i < size ; i++) {
                    error.append(e.getErrorList().get(i));
                    if (i > 0 && size > 1 && i != size-1) {
                        error.append(" ");
                    }
                }
                request.setAttribute("error", error.toString());
                this.getServletContext().getRequestDispatcher( "/profile.jsp" ).forward( request, response );
            }
        } else {
            response.sendRedirect(request.getContextPath()+"/profile?id="+id);
        }

    }
}