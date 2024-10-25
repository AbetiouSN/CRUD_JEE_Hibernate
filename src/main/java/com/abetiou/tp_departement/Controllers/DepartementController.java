package com.abetiou.tp_departement.Controllers;


import com.abetiou.tp_departement.DAO.DaoDepartement;
import com.abetiou.tp_departement.DAO.DaoEmploye;
import com.abetiou.tp_departement.Entity.Departement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletDispatcher", urlPatterns = "/departementservlet")
public class DepartementController extends HttpServlet {

    private DaoDepartement daoDepartement;
    private DaoEmploye daoEmploye;

    @Override
    public void init() {
        daoDepartement = new DaoDepartement();
        daoEmploye = new DaoEmploye();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                listDepartement(request, response);
                return;
            }
            switch (action) {
                case "add":
                    addDepartement(request, response);
                    break;
                case "list":
                    listDepartement(request, response);
                    break;
                case "delete":
                    deleteDepartement(request, response);
                    break;
                case "edit":
                    editDepartement(request, response);
                    break;
                case "update":
                    updateDepartement(request, response);
                    break;

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void addDepartement(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nom = request.getParameter("nom");
        Departement departement = new Departement();
        departement.setNom(nom);
        try {
            daoDepartement.save(departement);
            request.setAttribute("success", "Departementa ete ajoute avec succes !");
            request.getRequestDispatcher("departementservlet?action=list").forward(request,response);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void listDepartement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Departement> departements = daoDepartement.findAll();
        Map<Integer, Integer> departementEmployeCount = new HashMap<>();

        for (Departement departement : departements) {
            int nbrEmployes = daoEmploye.nbrEmploye(departement);
            departementEmployeCount.put(departement.getId(), nbrEmployes);
        }

        request.setAttribute("departementEmployeCount", departementEmployeCount);
        request.setAttribute("departements", departements);
        request.getRequestDispatcher("listeDepartement.jsp").forward(request, response);
    }

    private void deleteDepartement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
//        System.out.println("Deleting department with ID: " + id);
        daoDepartement.delete(id);
        request.setAttribute("success", "Departementa ete supprime avec succes !");
        request.getRequestDispatcher("departementservlet?action=list").forward(request,response);
    }

    private void editDepartement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Departement departement = daoDepartement.findById(id);

        request.setAttribute("departement", departement);
        request.setAttribute("isEdit", true);
        request.getRequestDispatcher("listeDepartement.jsp").forward(request, response);
    }

    private void updateDepartement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nom = request.getParameter("nom");
        Departement departement = new Departement();
        departement.setId(id);
        departement.setNom(nom);
        daoDepartement.update(departement);
        request.setAttribute("success", "Departementa ete modifie avec succes !");
        request.getRequestDispatcher("departementservlet?action=list").forward(request,response);
    }


}
