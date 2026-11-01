# Application web e-commerce — MVC2 & JPA

Application web Java qui simule une boutique en ligne : vitrine, panier et internaute.
Réalisée dans le cadre de l'atelier JEE du cycle ingénieur LSI — FSTT Tanger.

Le contrôleur suit le patron **MVC2** : une servlet par gestion, avec un paramètre `action`
qui dispatche les traitements. La couche persistance repose sur **JPA (EclipseLink)**.

## Aperçu

| | |
|---|---|
| ![Connexion](photos/rapport/01-connexion.png) | ![Vitrine](photos/rapport/02-vitrine.png) |
| ![Panier](photos/rapport/03-panier.png) | ![Base de données](photos/rapport/04-base-de-donnees.png) |

## Technologies

| Technologie | Version |
|---|---|
| Java | 21 |
| Jakarta Servlet | 6.1 (Tomcat 11) |
| JPA / EclipseLink | 3.2 / 5.0.1 |
| MySQL Connector/J | 9.1.0 |
| JSTL | 3.0 |
| Lombok | 1.18.34 |
| Maven | wrapper inclus |

## Structure

Le projet Maven se trouve dans le dossier `demo/`.

```
demo/
├── pom.xml
└── src/main/
    ├── java/ma/fstt/
    │   ├── model/         Internaute, Panier, LignePanier, Produit, Vitrine
    │   ├── dao/           Dao (transactions et requêtes JPQL)
    │   └── controller/    TableCreation, InternauteServlet, VitrineServlet, PanierServlet
    ├── resources/META-INF/persistence.xml
    └── webapp/
        ├── index.jsp          Connexion
        ├── inscription.jsp    Inscription
        ├── vitrine.jsp        Catalogue
        ├── panier.jsp         Panier
        ├── css/style.css
        └── WEB-INF/web.xml
```

## Modèle de données

| Entité | Attributs principaux | Relations |
|---|---|---|
| `Vitrine` | id, nomTheme | 1—N `Produit` |
| `Produit` | id, designation, prix, stock | N—1 `Vitrine` |
| `Internaute` | id, nom, email, motDePasse | 1—1 `Panier` |
| `Panier` | id, dateCreation | 1—1 `Internaute`, 1—N `LignePanier` |
| `LignePanier` | id, quantite | N—1 `Panier`, N—1 `Produit` |

## Contrôleurs

| Servlet | Action | Rôle |
|---|---|---|
| `InternauteServlet` | `connecter` (POST) | Connexion de l'internaute |
| | `inscrire` (POST) | Création du compte puis connexion |
| | GET | Déconnexion |
| `VitrineServlet` | — | Liste des vitrines et des produits |
| | `ajouter` | Ajout d'un produit |
| | `supprimer` | Suppression d'un produit |
| `PanierServlet` | — | Contenu du panier et total |
| | `ajouter` | Ajout d'un produit (quantité incrémentée) |
| | `supprimer` | Suppression d'une ligne |
| | `vider` | Vidage du panier |

La classe `Dao` centralise les transactions JPA (`tx`, `list`, `one`) et sert de lien
entre la couche modèle et les servlets.

## Base de données

La configuration se trouve dans `demo/src/main/resources/META-INF/persistence.xml` :

- unité de persistance : `default`
- base : `G-ecom` sur `localhost:3306`
- utilisateur : `root`, sans mot de passe
- génération du schéma : `drop-and-create`

La base doit exister avant le premier lancement :

```sql
CREATE DATABASE `G-ecom`;
```

`TableCreation` crée l'`EntityManagerFactory` au démarrage de l'application et insère
des données de démonstration : 2 vitrines et 5 produits.

> Le schéma est recréé à chaque déploiement, donc les données de démonstration sont
> réinsérées et les comptes créés précédemment sont perdus.

## Lancer le projet

Prérequis : JDK 21, MySQL et Tomcat 11.

```bash
cd demo
./mvnw clean package
```

Déployer ensuite `target/demo-1.0-SNAPSHOT.war` dans Tomcat, ou lancer la configuration
Tomcat depuis IntelliJ IDEA. L'application est accessible sur l'URL du contexte déployé,
par exemple `http://localhost:8081/demo_war_exploded/`.

Créer un compte depuis la page d'inscription, puis naviguer dans la vitrine et le panier.

## Auteur

Omar — Cycle Ingénieur LSI, FSTT Tanger
