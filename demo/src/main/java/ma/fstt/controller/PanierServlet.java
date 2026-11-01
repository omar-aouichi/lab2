package ma.fstt.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.dao.Dao;
import ma.fstt.model.Internaute;
import ma.fstt.model.LignePanier;
import ma.fstt.model.Panier;
import ma.fstt.model.Produit;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/PanierServlet")
public class PanierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Internaute internaute = (Internaute) request.getSession().getAttribute("internaute");
        if (internaute == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String action = request.getParameter("action");
        if ("ajouter".equals(action)) {
            Long produitId = Long.valueOf(request.getParameter("produit"));
            Dao.tx(em -> {
                Produit produit = em.find(Produit.class, produitId);
                if (produit == null) {
                    return null;
                }
                Panier panier = panier(em, internaute);
                LignePanier ligne = em.createQuery("select l from LignePanier l where l.panier.id = ?1 and l.produit.id = ?2", LignePanier.class)
                        .setParameter(1, panier.getId())
                        .setParameter(2, produitId)
                        .getResultList().stream().findFirst().orElse(null);
                if (ligne == null) {
                    em.persist(new LignePanier(null, 1, panier, produit));
                } else {
                    ligne.setQuantite(ligne.getQuantite() + 1);
                }
                return null;
            });
            response.sendRedirect("PanierServlet");
            return;
        }
        if ("supprimer".equals(action)) {
            Long id = Long.valueOf(request.getParameter("id"));
            Dao.tx(em -> {
                em.remove(em.find(LignePanier.class, id));
                return null;
            });
            response.sendRedirect("PanierServlet");
            return;
        }
        if ("vider".equals(action)) {
            Dao.tx(em -> {
                em.createQuery("delete from LignePanier l where l.panier.internaute.id = ?1").setParameter(1, internaute.getId()).executeUpdate();
                em.createQuery("delete from Panier p where p.internaute.id = ?1").setParameter(1, internaute.getId()).executeUpdate();
                return null;
            });
            response.sendRedirect("PanierServlet");
            return;
        }
        Panier panier = Dao.one("select p from Panier p where p.internaute.id = ?1", Panier.class, internaute.getId());
        request.setAttribute("lignes", panier == null ? List.of() : Dao.list("select l from LignePanier l where l.panier.id = ?1", LignePanier.class, panier.getId()));
        request.getRequestDispatcher("panier.jsp").forward(request, response);
    }

    private Panier panier(EntityManager em, Internaute internaute) {
        List<Panier> paniers = em.createQuery("select p from Panier p where p.internaute.id = ?1", Panier.class)
                .setParameter(1, internaute.getId())
                .getResultList();
        if (paniers.isEmpty()) {
            Panier panier = new Panier(null, new Date(), em.find(Internaute.class, internaute.getId()), null);
            em.persist(panier);
            return panier;
        }
        return paniers.get(0);
    }
}
