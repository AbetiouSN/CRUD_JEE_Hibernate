<%@ page import="com.abetiou.tp_departement.Entity.User" %>
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
    <title>Liste des Départements</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <jsp:include page="nav.jsp" />

    <!-- Formulaire pour ajouter ou modifier un employé -->
    <form action="departementservlet?action=${isEdit ? 'update' : 'add'}" method="post">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom De Departement</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${departement.nom}" required>

            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${departement.id}">
            </c:if>
        </div>
        <button type="submit" class="btn btn-primary">${isEdit ? 'Modifie' : 'Ajouter'}</button>
    </form>

    <hr>
    <h4 class="text-center mb-4">Liste des Départements</h4>
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
            <th scope="col">Nombre des employes</th>
            <th SCOPE="col">AFFICHER EMPLOYES</th>
            <th scope="col">VOIR LA LISTE DES EMPLOYES</th>
            <th scope="col">ACTION</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="departement" items="${departements}">
            <tr>
                <td>${departement.id}</td>
                <td>${departement.nom}</td>
                <td>${departementEmployeCount[departement.id]}</td>  <!-- valeur (nbr d'employe dyal dep li 3ndha id departement.id) -->
                <td><a href="ItemServlet?action=employs&id=${departement.id}" class="btn btn-warning btn-sm">Afficher</a></td>
                <td>
                    <a href="ajouterEmploye.jsp?id=${departement.id}" class="btn btn-primary btn-sm">Ajouter Un Employe</a>
                </td>
                <td>
                    <a href="departementservlet?action=edit&id=${departement.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <a href="departementservlet?action=delete&id=${departement.id}" class="btn btn-danger btn-sm"
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce département ?');">
                        Supprimer
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
