package bancodiario.datos;

import bancodiario.modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection con;

    public UsuarioDAO() {
        this.con = Conexion.getConexion();
    }

    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, email, contrasena, telefono, estado, id_rol) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEstado());
            ps.setInt(6, u.getIdRol());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, email=?, telefono=?, estado=?, id_rol=? WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getEstado());
            ps.setInt(5, u.getIdRol());
            ps.setInt(6, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY id_usuario";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public Usuario login(String email, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE email=? AND contrasena=? AND estado='activo'";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error login: " + e.getMessage());
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setContrasena(rs.getString("contrasena"));
        u.setTelefono(rs.getString("telefono"));
        u.setEstado(rs.getString("estado"));
        u.setFechaRegistro(rs.getDate("fecha_registro"));
        u.setIdRol(rs.getInt("id_rol"));
        return u;
    }
}
