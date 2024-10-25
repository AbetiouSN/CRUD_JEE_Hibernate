package com.abetiou.tp_departement.Controllers;


import com.abetiou.tp_departement.DAO.DaoDepartement;
import com.abetiou.tp_departement.DAO.DaoEmploye;
import com.abetiou.tp_departement.DAO.DaoUser;
import com.abetiou.tp_departement.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final DaoUser daoUser = new DaoUser();

    @Override
    public void init()   {
        daoUser.createAdmin();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = daoUser.login(email, password);

        if (user != null) {
            // Création de la session utilisateur
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // Redirection ver home
            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

