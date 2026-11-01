<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Vitrine</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<main class="container">
    <div class="topbar">
        <h1>Vitrine</h1>
        <nav>
            <c:if test="${not empty sessionScope.internaute}">
                <span>Bonjour ${sessionScope.internaute.nom}</span>
            </c:if>
            <a href="PanierServlet">Panier</a>
            <a href="InternauteServlet">Déconnexion</a>
        </nav>
    </div>
    <table>
        <tr>
            <th>Produit</th>
            <th>Vitrine</th>
            <th>Prix</th>
            <th>Stock</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="p" items="${produits}">
            <tr>
                <td>${p.designation}</td>
                <td>${p.vitrine.nomTheme}</td>
                <td>${p.prix}</td>
                <td>${p.stock}</td>
                <td><a href="PanierServlet?action=ajouter&produit=${p.id}">Ajouter au panier</a></td>
                <td><a href="VitrineServlet?action=supprimer&id=${p.id}">Supprimer</a></td>
            </tr>
        </c:forEach>
    </table>
    <h2>Ajouter un produit</h2>
    <form class="row-form" method="get" action="VitrineServlet">
        <input type="hidden" name="action" value="ajouter">
        <label>Désignation <input type="text" name="designation" required></label>
        <label>Prix <input type="number" name="prix" step="0.01" required></label>
        <label>Stock <input type="number" name="stock" required></label>
        <label>Vitrine
            <select name="vitrine">
                <c:forEach var="v" items="${vitrines}">
                    <option value="${v.id}">${v.nomTheme}</option>
                </c:forEach>
            </select>
        </label>
        <button>Ajouter</button>
    </form>
</main>
</body>
</html>
