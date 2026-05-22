package bancodiario.servlet;

import bancodiario.facade.BancoDiarioFacade;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * PrestamoServlet - Capa de Presentación (Servlet)
 *
 * Ahora solo habla con BancoDiarioFacade.
 * No conoce PrestamoService, PagoService ni ningún DAO directamente.
 */
public class PrestamoServlet extends HttpServlet {

    // Un solo punto de acceso: el Facade
    private final BancoDiarioFacade facade = BancoDiarioFacade.getInstancia();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); return;
        }
        Usuario u = (Usuario) session.getAttribute("usuario");
        String accion = req.getPathInfo();
        if (accion == null) accion = "/lista";

        switch (accion) {
            case "/lista":
                // Facade coordina PrestamoService + PagoService internamente
                req.setAttribute("prestamos", facade.listarPrestamos());
                req.setAttribute("pagos", facade.listarTodosPagos());
                req.getRequestDispatcher("/jsp/prestamista/dashboard.jsp").forward(req, resp);
                break;
            case "/solicitar":
                req.setAttribute("solicitudes", facade.listarSolicitudesDeUsuario(u.getIdUsuario()));
                req.setAttribute("pagos", facade.listarTodosPagos());
                req.getRequestDispatcher("/jsp/prestatario/dashboard.jsp").forward(req, resp);
                break;
            case "/eliminar":
                facade.eliminarPrestamo(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/prestamo/lista?msg=eliminado");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/prestamo/solicitar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); return;
        }
        Usuario u = (Usuario) session.getAttribute("usuario");
        String accion = req.getPathInfo();

        if ("/solicitar".equals(accion)) {
            double monto   = Double.parseDouble(req.getParameter("monto"));
            int plazo      = Integer.parseInt(req.getParameter("plazo"));
            double interes = Double.parseDouble(req.getParameter("interes"));
            // Facade valida y delega a PrestamoService internamente
            boolean ok = facade.solicitarPrestamo(u.getIdUsuario(), monto, plazo, interes);
            resp.sendRedirect(req.getContextPath() + "/prestamo/solicitar" + (ok ? "?msg=solicitado" : "?msg=error"));

        } else if ("/simular".equals(accion)) {
            double monto   = Double.parseDouble(req.getParameter("monto"));
            int plazo      = Integer.parseInt(req.getParameter("plazo"));
            double interes = Double.parseDouble(req.getParameter("interes"));
            // Facade delega la simulación al PrestamoService
            double[] resultado = facade.simularCredito(monto, interes, plazo);
            session.setAttribute("simMonto", monto);
            session.setAttribute("simPlazo", plazo);
            session.setAttribute("simInteres", interes);
            session.setAttribute("simCuota", resultado[0]);
            session.setAttribute("simTotal", resultado[1]);
            session.setAttribute("simInteresTotal", resultado[2]);
            resp.sendRedirect(req.getContextPath() + "/prestamo/solicitar?simular=1");
        }
    }
}
