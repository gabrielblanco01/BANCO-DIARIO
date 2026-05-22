package bancodiario.servlet;

import bancodiario.logica.SoporteService;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SoporteServlet extends HttpServlet {

    private SoporteService soporteService = new SoporteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 6)) return;
        String accion = req.getPathInfo();
        if (accion == null) accion = "/incidencias";

        if ("/incidencias".equals(accion)) {
            req.setAttribute("incidencias", soporteService.obtenerIncidenciasAbiertas());
            req.getRequestDispatcher("/jsp/soporte/dashboard.jsp").forward(req, resp);
        } else if ("/cerrar".equals(accion)) {
            soporteService.cerrarIncidencia(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/soporte/incidencias?msg=cerrado");
        } else if ("/todas".equals(accion)) {
            req.setAttribute("incidencias", soporteService.obtenerTodasIncidencias());
            req.getRequestDispatcher("/jsp/soporte/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 6)) return;
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Usuario u = (Usuario) session.getAttribute("usuario");
        int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
        String descripcion = req.getParameter("descripcion");
        soporteService.reportarIncidencia(idUsuario, descripcion);
        resp.sendRedirect(req.getContextPath() + "/soporte/incidencias?msg=reportado");
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
