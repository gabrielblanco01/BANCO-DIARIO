package bancodiario.servlet;

import bancodiario.logica.PrestamoService;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AnalistaServlet extends HttpServlet {

    private PrestamoService prestamoService = new PrestamoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 4)) return;
        req.setAttribute("solicitudes", prestamoService.obtenerSolicitudesPendientes());
        req.getRequestDispatcher("/jsp/analista/solicitudes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 4)) return;
        String accion = req.getPathInfo();
        int idSolicitud = Integer.parseInt(req.getParameter("idSolicitud"));

        if ("/aprobar".equals(accion)) {
            prestamoService.aprobarSolicitud(idSolicitud);
            resp.sendRedirect(req.getContextPath() + "/analista/solicitudes?msg=aprobado");
        } else if ("/rechazar".equals(accion)) {
            prestamoService.rechazarSolicitud(idSolicitud);
            resp.sendRedirect(req.getContextPath() + "/analista/solicitudes?msg=rechazado");
        }
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
