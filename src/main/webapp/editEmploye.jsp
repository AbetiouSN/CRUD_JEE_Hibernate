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
    <title>Modifier un employe</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <jsp:include page="nav.jsp" />

    <h1 class="text-center mb-4">Modifier un employe</h1>

    <!-- Formulaire pour modifier un employé -->
    <form action="ItemServlet?action=update" method="post">
        <!-- Champ caché pour stocker l'ID de l'employé -->
        <input type="hidden" name="idEmploye" value="${employe.id}">

        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${employe.idUser.nom}" required>
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prenom</label>
            <input type="text" class="form-control" id="prenom" name="prenom" value="${employe.idUser.prenom}" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${employe.idUser.email}" required>
        </div>
        <div class="mb-3">
            <label for="salaire" class="form-label">Salaire</label>
            <input type="number" class="form-control" id="salaire" name="salaire" value="${employe.salaire}" step="0.01" required>
        </div>
<%--        <div class="mb-3">--%>
<%--            <label for="idDep" class="form-label">Departement</label>--%>
<%--            <select class="form-select" id="departement" name="idDep" required>--%>
<%--                <c:forEach var="departement" items="${departements}">--%>
<%--                    <option value="${departement.id}">${departement.nom}</option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>
<%--        </div>--%>

        <div class="mb-3">
            <label for="idDep" class="form-label">Departement</label>
            <select class="form-select" id="departement" name="idDep" required>
                <c:forEach var="departement" items="${departements}">
                    <option value="${departement.id}"
                            <c:if test="${departement.id == employe.idDep.id}">selected</c:if>
                    >${departement.nom}</option>
                </c:forEach>
            </select>
        </div>


        <button type="submit" class="btn btn-primary">Mettre a jour</button>
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
