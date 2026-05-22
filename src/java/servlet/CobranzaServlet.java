package bancodiario.servlet;

import bancodiario.logica.SoporteService;
import bancodiario.logica.PrestamoService;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CobranzaServlet extends HttpServlet {

    private SoporteService soporteService = new SoporteService();
    private PrestamoService prestamoService = new PrestamoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 5)) return;
        req.setAttribute("cobranzas", soporteService.obtenerCobranzasAbiertas());
        req.setAttribute("vencidos", prestamoService.obtenerPorEstado("vencido"));
        req.getRequestDispatcher("/jsp/cobranza/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 5)) return;
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Usuario u = (Usuario) session.getAttribute("usuario");
        int idPrestamo = Integer.parseInt(req.getParameter("idPrestamo"));
        String comentario = req.getParameter("comentario");
        soporteService.registrarCobranza(idPrestamo, u.getIdUsuario(), comentario);
        resp.sendRedirect(req.getContextPath() + "/cobranza/lista?msg=registrado");
    }

    private boolean autorizado(HttpServletRequest req, HttpServletResponse resp, int rol)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); return false;
        }
        Usuario u = (Usuario) s.getAttribute("usuario");
        if (u.getIdRol() != rol) { resp.sendRedirect(req.getContextPath() + "/login"); return false; }
        return true;
    }
}
