<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inscription</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<main class="container">
    <h1>Boutique</h1>
    <form class="form" method="post" action="InternauteServlet">
        <h2>Inscription</h2>
        <input type="hidden" name="action" value="inscrire">
        <p><label>Nom <input type="text" name="nom" required autofocus></label></p>
        <p><label>Email <input type="email" name="email" required></label></p>
        <p><label>Mot de passe <input type="password" name="motDePasse" required></label></p>
        <button>S'inscrire</button>
    </form>
    <p class="links"><a href="index.jsp">Déjà un compte ? Se connecter</a></p>
</main>
</body>
</html>
