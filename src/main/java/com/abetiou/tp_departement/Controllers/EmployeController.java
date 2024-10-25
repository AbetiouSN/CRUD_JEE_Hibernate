package com.abetiou.tp_departement.Controllers;


import com.abetiou.tp_departement.DAO.DaoDepartement;
import com.abetiou.tp_departement.DAO.DaoEmploye;
import com.abetiou.tp_departement.DAO.DaoUser;
import com.abetiou.tp_departement.Entity.Departement;
import com.abetiou.tp_departement.Entity.Employe;
import com.abetiou.tp_departement.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ItemServlet", urlPatterns = "/ItemServlet")
public class EmployeController extends HttpServlet {

    private DaoEmploye daoEmploye;
    private DaoDepartement daoDepartement;
    private DaoUser daoUser;
    @Override
    public void init()   {
        daoEmploye = new DaoEmploye();
        daoDepartement = new DaoDepartement();
        daoUser = new DaoUser();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGet called");
        processRequest(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            listeEmploye(request, response);
            return;
        }

        switch (action) {
            case "add":
                addEmploye(request, response);
                break;
            case "addwithotid":
                listDepartement(request,response);
                break;
            case "list":
                listeEmploye(request, response);
                break;
            case "employs":
                listeEmployes(request, response);
                break;
            case "delete":
                deleteEmploye(request, response);
                break;
            case "edit":
                editEmploye(request, response);
                break;
            case "update":
                updateEmploye(request, response);
                break;

        }
    }

    private void addEmploye(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String nom = request.getParameter("nom");
        String prenom  = request.getParameter("prenom");
        String email = request.getParameter("email");

        double salaire = Double.parseDouble(request.getParameter("salaire"));

        int id = Integer.parseInt(request.getParameter("id"));

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);

//        Departement departement = daoDepartement.findById(id);

        Employe employe = new Employe();
        employe.setIdUser(user);
//        employe.setIdDep(departement);
        employe.setSalaire(salaire);
        try{
            daoEmploye.Add(employe,id);
            request.setAttribute("success", "L'employe a ete ajoute avec succes !");
            request.getRequestDispatcher("ItemServlet?action=list").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur s'est produite lors de l'ajout de l'employe.");
            request.getRequestDispatcher("ItemServlet?action=list").forward(request,response);
        }
    }


private void listeEmployes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    List<Employe> employes = daoEmploye.FindEmploysByIdDep(id);
    request.setAttribute("employes", employes);
    request.getRequestDispatcher("listeEmployeDepartement.jsp").forward(request, response);
}




    private void listeEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employe> employes = daoEmploye.findAll();
        request.setAttribute("employes",employes);
        request.getRequestDispatcher("listeEmployeDepartement.jsp").forward(request, response);
    }

    private void deleteEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        daoEmploye.delete(id);
        request.setAttribute("success", "Employe ete supprime avec succes !");
        request.getRequestDispatcher("ItemServlet?action=list").forward(request, response);
    }



    private void listDepartement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Departement> departements = daoDepartement.findAll();
        request.setAttribute("departements", departements);
        request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);
    }



    private void editEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employe employe = daoEmploye.findById(id);
        List<Departement> departements = daoDepartement.findAll();

        if (employe != null) {
            request.setAttribute("employe", employe);
            request.setAttribute("departements", departements);
            request.getRequestDispatcher("editEmploye.jsp").forward(request, response);
        } else {
            // Gestion du cas où l'employé n'existe pas
            request.setAttribute("error", "Employé non trouvé.");
            response.sendRedirect("ItemServlet?action=list");
        }
    }



    private void updateEmploye(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("idEmploye"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String salaireStr = request.getParameter("salaire");
            String departementIdStr = request.getParameter("idDep");


//            if (salaireStr == null || departementIdStr == null) {
//                request.setAttribute("error", "Salaire ou ID de département est manquant.");
//                request.getRequestDispatcher("editEmploye.jsp").forward(request, response);
//                return;
//            }

            double salaire = Double.parseDouble(salaireStr);
            int departementId = Integer.parseInt(departementIdStr);

            Employe employe = daoEmploye.findById(id);
            employe.getIdUser().setNom(nom);
            employe.getIdUser().setPrenom(prenom);
            employe.getIdUser().setEmail(email);
            employe.setSalaire(salaire);
            employe.setIdDep(daoDepartement.findById(departementId));

            daoEmploye.update(employe);
//            response.sendRedirect("ItemServlet?action=list");
            request.setAttribute("success", "Employe ete modifie avec succes !");
            request.getRequestDispatcher("ItemServlet?action=list").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Valeur invalide pour salaire ou ID.");
            request.getRequestDispatcher("editEmploye.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la mise à jour de l'employé.");
            request.getRequestDispatcher("editEmploye.jsp").forward(request, response);
        }
    }


}
