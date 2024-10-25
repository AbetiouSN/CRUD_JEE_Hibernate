<%@ page import="com.abetiou.tp_departement.Entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Ajouter un employe</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <jsp:include page="nav.jsp" />
    <h1 class="text-center mb-4">Ajouter un employe</h1>

    <!-- Formulaire pour ajouter un employé -->
    <form action="ItemServlet?action=add" method="post">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prenom</label>
            <input type="text" class="form-control" id="prenom" name="prenom" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="id" class="form-label">Departement</label>

            <c:if test="${empty param.id}">
                <!-- Si param.id est vide, afficher la liste déroulante pour choisir un département -->
                <select class="form-select" id="departement" name="id" required>
                    <!-- Remplir cette liste avec les départements depuis la base de données -->
                    <c:forEach var="departement" items="${departements}">
                        <option value="${departement.id}">${departement.nom}</option>
                    </c:forEach>
                </select>
            </c:if>

            <c:if test="${not empty param.id}">
                <!-- Si param.id n'est pas vide, afficher un champ readonly avec l'ID du département -->
                <input type="number" class="form-control" id="id" name="id" value="${param.id}" readonly>
            </c:if>

        </div>



        <div class="mb-3">
            <label for="salaire" class="form-label">Salaire</label>
            <input type="number" class="form-control" id="salaire" name="salaire" step="0.01" required>
        </div>


        <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>

    <c:if test="${not empty success}">
        <div class="alert alert-success mt-3">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

