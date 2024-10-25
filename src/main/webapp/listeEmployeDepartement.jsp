<%@ page import="com.abetiou.tp_departement.Entity.User" %><%--
  Created by IntelliJ IDEA.
  User: PE
  Date: 23/10/2024
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%
    // Vérification de l'authentification de l'utilisateur
    User user = (User) session.getAttribute("user");
    if (user == null) {
        // Si l'utilisateur n'est pas authentifié, redirigez vers login.jsp
        response.sendRedirect("login.jsp");
        return; // Arrête le traitement de la page
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des employes</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <jsp:include page="nav.jsp" />
    <h1 class="text-center mb-4">Liste des employes selon departement</h1>

    <div>
        <a class="btn btn-primary" href="ItemServlet?action=addwithotid">Ajouter Un Employe</a>
    </div>
    <hr>
    <br>
    <c:if test="${not empty success}">
        <div class="mb-3">
            <label class="form-label">${success}</label>
        </div>
    </c:if>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Nom</th>
            <th scope="col">PREONOM</th>
            <th scope="col">EMAIL</th>
            <th scope="col">DEPARTEMENT</th>
            <th scope="col">SALAIRE</th>
            <th scope="col">ACTION</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="employe" items="${employes}">
            <tr>
                <td>${employe.id}</td>
                <td>${employe.idUser.nom}</td>
                <td>${employe.idUser.prenom}</td>
                <td>${employe.idUser.email}</td>
                <td>${employe.idDep.nom}</td>
                <td>${employe.salaire} DH</td>
                <td>
                    <a href="ItemServlet?action=edit&id=${employe.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <a href="ItemServlet?action=delete&id=${employe.id}" class="btn btn-danger btn-sm"
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet employé ?');">
                        Supprimer
                    </a>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Bootstrap JS and dependencies (if needed) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

