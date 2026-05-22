package bancodiario.servlet;

import bancodiario.logica.UsuarioService;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import bancodiario.datos.LogDAO;

public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Si ya hay sesión, redirigir al panel
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            redirigirPorRol((Usuario) session.getAttribute("usuario"), resp, req);
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String contrasena = req.getParameter("contrasena");

        Usuario u = usuarioService.autenticar(email, contrasena);

        if (u == null) {
            LogDAO.registrarActividad("LOGIN_FALLIDO", email, "Credenciales incorrectas");
            req.setAttribute("error", "Correo o contraseña incorrectos.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("usuario", u);
        session.setAttribute("idUsuario", u.getIdUsuario());
        session.setAttribute("rol", u.getIdRol());
        session.setAttribute("nombre", u.getNombre());
LogDAO.registrarActividad("LOGIN_EXITOSO", u.getEmail(), "Acceso como rol " + u.getIdRol());
redirigirPorRol(u, resp, req);
    }

    private void redirigirPorRol(Usuario u, HttpServletResponse resp, HttpServletRequest req)
            throws IOException {
        String base = req.getContextPath();
        switch (u.getIdRol()) {
            case 1: resp.sendRedirect(base + "/admin/dashboard"); break;
            case 2: resp.sendRedirect(base + "/prestamo/lista"); break;
            case 3: resp.sendRedirect(base + "/prestamo/solicitar"); break;
            case 4: resp.sendRedirect(base + "/analista/solicitudes"); break;
            case 5: resp.sendRedirect(base + "/cobranza/lista"); break;
            case 6: resp.sendRedirect(base + "/soporte/incidencias"); break;
            default: resp.sendRedirect(base + "/login");
        }
    }
}
