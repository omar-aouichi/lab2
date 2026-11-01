package ma.fstt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.dao.Dao;
import ma.fstt.model.Produit;
import ma.fstt.model.Vitrine;

import java.io.IOException;

@WebServlet("/VitrineServlet")
public class VitrineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("ajouter".equals(action)) {
            String designation = request.getParameter("designation");
            double prix = Double.parseDouble(request.getParameter("prix"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            Long vitrineId = Long.valueOf(request.getParameter("vitrine"));
            Dao.tx(em -> {
                em.persist(new Produit(null, designation, prix, stock, em.find(Vitrine.class, vitrineId)));
                return null;
            });
            response.sendRedirect("VitrineServlet");
            return;
        }
        if ("supprimer".equals(action)) {
            Long id = Long.valueOf(request.getParameter("id"));
            Dao.tx(em -> {
                em.createQuery("delete from LignePanier l where l.produit.id = ?1").setParameter(1, id).executeUpdate();
                Produit produit = em.find(Produit.class, id);
                if (produit != null) {
                    em.remove(produit);
                }
                return null;
            });
            response.sendRedirect("VitrineServlet");
            return;
        }
        request.setAttribute("vitrines", Dao.list("select v from Vitrine v", Vitrine.class));
        request.setAttribute("produits", Dao.list("select p from Produit p", Produit.class));
        request.getRequestDispatcher("vitrine.jsp").forward(request, response);
    }
}
