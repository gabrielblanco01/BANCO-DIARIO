package bancodiario.servlet;

import bancodiario.logica.UsuarioService;
import bancodiario.modelo.Usuario;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 1)) return;

        String accion = req.getPathInfo();
        if (accion == null) accion = "/dashboard";

        switch (accion) {
            case "/dashboard":
                List<Usuario> usuarios = usuarioService.obtenerTodos();
                req.setAttribute("usuarios", usuarios);
                req.setAttribute("totalUsuarios", usuarios.size());
                req.getRequestDispatcher("/jsp/admin/dashboard.jsp").forward(req, resp);
                break;
            case "/eliminar":
                int id = Integer.parseInt(req.getParameter("id"));
                usuarioService.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard?msg=eliminado");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!autorizado(req, resp, 1)) return;
        req.setCharacterEncoding("UTF-8");

        String accion = req.getPathInfo();
        if ("/crear".equals(accion)) {
            String nombre = req.getParameter("nombre");
            String email = req.getParameter("email");
            String pass = req.getParameter("contrasena");
            String tel = req.getParameter("telefono");
            int rol = Integer.parseInt(req.getParameter("idRol"));
            boolean ok = usuarioService.registrar(nombre, email, pass, tel, rol);
            String msg = ok ? "?msg=creado" : "?msg=error";
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard" + msg);
        } else if ("/editar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("idUsuario"));
            Usuario u = usuarioService.obtenerPorId(id);
            if (u != null) {
                u.setNombre(req.getParameter("nombre"));
                u.setEmail(req.getParameter("email"));
                u.setTelefono(req.getParameter("telefono"));
                u.setIdRol(Integer.parseInt(req.getParameter("idRol")));
                u.setEstado(req.getParameter("estado"));
                usuarioService.actualizar(u);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?msg=editado");
        }
    }

    private boolean autorizado(HttpServletRequest req, HttpServletResponse resp, int rolRequerido)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        Usuario u = (Usuario) s.getAttribute("usuario");
        if (u.getIdRol() != rolRequerido) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
