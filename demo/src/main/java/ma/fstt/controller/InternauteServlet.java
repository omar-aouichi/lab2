package ma.fstt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.dao.Dao;
import ma.fstt.model.Internaute;

import java.io.IOException;

@WebServlet("/InternauteServlet")
public class InternauteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        Internaute internaute = Dao.one("select i from Internaute i where i.email = ?1 and i.motDePasse = ?2", Internaute.class, email, motDePasse);
        if (internaute == null && "inscrire".equals(action)) {
            internaute = Dao.tx(em -> {
                Internaute nouveau = new Internaute(null, request.getParameter("nom"), email, motDePasse, null);
                em.persist(nouveau);
                return nouveau;
            });
        }
        if (internaute == null) {
            request.setAttribute("erreur", "Email ou mot de passe incorrect");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        request.getSession().setAttribute("internaute", internaute);
        response.sendRedirect("VitrineServlet");
    }
}
