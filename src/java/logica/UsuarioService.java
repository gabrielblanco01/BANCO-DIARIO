package bancodiario.logica;

import bancodiario.datos.UsuarioDAO;
import bancodiario.modelo.Usuario;
import java.util.List;

public class UsuarioService {

    private UsuarioDAO dao;

    public UsuarioService() {
        this.dao = new UsuarioDAO();
    }

    public boolean registrar(String nombre, String email, String contrasena, String telefono, int idRol) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (email == null || !email.contains("@")) return false;
        if (contrasena == null || contrasena.length() < 4) return false;
        Usuario u = new Usuario(nombre.trim(), email.trim(), contrasena, telefono, idRol);
        return dao.insertar(u);
    }

    public Usuario autenticar(String email, String contrasena) {
        if (email == null || contrasena == null) return null;
        return dao.login(email.trim(), contrasena);
    }

    public boolean actualizar(Usuario u) {
        return dao.actualizar(u);
    }

    public boolean eliminar(int idUsuario) {
        return dao.eliminar(idUsuario);
    }

    public List<Usuario> obtenerTodos() {
        return dao.listarTodos();
    }

    public Usuario obtenerPorId(int id) {
        return dao.buscarPorId(id);
    }
}
