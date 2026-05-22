package bancodiario.servlet;

import bancodiario.logica.PagoService;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PagoServlet extends HttpServlet {

    private PagoService pagoService = new PagoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); return;
        }
        String accion = req.getPathInfo();
        if ("/registrar".equals(accion)) {
            int idPrestamo = Integer.parseInt(req.getParameter("idPrestamo"));
            double monto = Double.parseDouble(req.getParameter("monto"));
            boolean ok = pagoService.registrarPago(idPrestamo, monto);
            String msg = ok ? "?msg=pagado" : "?msg=error";
            resp.sendRedirect(req.getContextPath() + "/prestamo/solicitar" + msg);
        } else if ("/eliminar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            pagoService.eliminarPago(id);
            resp.sendRedirect(req.getContextPath() + "/prestamo/lista?msg=pagoeliminado");
        }
    }
}
