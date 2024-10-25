<%@ page import="com.abetiou.tp_departement.Entity.User" %><%--
  Created by IntelliJ IDEA.
  User: PE
  Date: 24/10/2024
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Home</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <!-- Navigation Bar -->
    <jsp:include page="nav.jsp" />

    <!-- Content Area -->
    <div id="content" class="mt-4">
        <c:choose>
            <c:when test="${user != null}">
                <h4>Bienvenue sur la page d'accueil !</h4>
                <h2>Welcome, <%= user.getNom() %>!</h2>
            </c:when>
            <c:otherwise>
                <h4>Vous devez vous connecter.</h4>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

