package ma.fstt.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ma.fstt.model.Produit;
import ma.fstt.model.Vitrine;

@WebListener
public class TableCreation implements ServletContextListener {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory emf() {
        return emf;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Vitrine informatique = new Vitrine(null, "Informatique", null);
        Vitrine telephonie = new Vitrine(null, "Téléphonie", null);
        em.persist(informatique);
        em.persist(telephonie);
        em.persist(new Produit(null, "PC Portable HP", 8500, 10, informatique));
        em.persist(new Produit(null, "Souris Logitech", 150, 50, informatique));
        em.persist(new Produit(null, "Clavier Mécanique", 450, 25, informatique));
        em.persist(new Produit(null, "iPhone 15", 12000, 5, telephonie));
        em.persist(new Produit(null, "Samsung Galaxy S24", 9500, 8, telephonie));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        emf.close();
    }
}
