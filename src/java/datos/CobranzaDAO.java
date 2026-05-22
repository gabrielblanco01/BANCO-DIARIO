package bancodiario.datos;

import bancodiario.modelo.Cobranza;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CobranzaDAO {

    private Connection con;

    public CobranzaDAO() {
        this.con = Conexion.getConexion();
    }

    public boolean insertar(Cobranza c) {
        String sql = "INSERT INTO cobranza (id_prestamo, id_usuario, estado, comentario) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getIdPrestamo());
            ps.setInt(2, c.getIdUsuario());
            ps.setString(3, c.getEstado());
            ps.setString(4, c.getComentario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar cobranza: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int idCobranza, String estado) {
        String sql = "UPDATE cobranza SET estado=? WHERE id_cobranza=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idCobranza);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar cobranza: " + e.getMessage());
            return false;
        }
    }

    public List<Cobranza> listarAbiertas() {
        List<Cobranza> lista = new ArrayList<>();
        String sql = "SELECT * FROM cobranza WHERE estado='abierto' ORDER BY fecha";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar cobranzas: " + e.getMessage());
        }
        return lista;
    }

    private Cobranza mapear(ResultSet rs) throws SQLException {
        Cobranza c = new Cobranza();
        c.setIdCobranza(rs.getInt("id_cobranza"));
        c.setIdPrestamo(rs.getInt("id_prestamo"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setEstado(rs.getString("estado"));
        c.setFecha(rs.getDate("fecha"));
        c.setComentario(rs.getString("comentario"));
        return c;
    }
}
