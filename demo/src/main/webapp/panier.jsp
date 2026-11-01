<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Panier</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<main class="container">
    <div class="topbar">
        <h1>Panier de ${sessionScope.internaute.nom}</h1>
        <nav>
            <a href="VitrineServlet">Vitrine</a>
        </nav>
    </div>
    <c:choose>
        <c:when test="${empty lignes}">
            <p class="empty">Votre panier est vide.</p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Produit</th>
                    <th>Prix</th>
                    <th>Quantité</th>
                    <th>Total</th>
                    <th></th>
                </tr>
                <c:set var="total" value="0"/>
                <c:forEach var="l" items="${lignes}">
                    <tr>
                        <td>${l.produit.designation}</td>
                        <td>${l.produit.prix}</td>
                        <td>${l.quantite}</td>
                        <td>${l.produit.prix * l.quantite}</td>
                        <td><a href="PanierServlet?action=supprimer&id=${l.id}">Supprimer</a></td>
                    </tr>
                    <c:set var="total" value="${total + l.produit.prix * l.quantite}"/>
                </c:forEach>
                <tr>
                    <td colspan="3">Total</td>
                    <td>${total}</td>
                    <td></td>
                </tr>
            </table>
            <p class="links"><a href="PanierServlet?action=vider">Vider le panier</a></p>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>
