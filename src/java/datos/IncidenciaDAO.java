package bancodiario.datos;

import bancodiario.modelo.Incidencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private Connection con;

    public IncidenciaDAO() {
        this.con = Conexion.getConexion();
    }

    public boolean insertar(Incidencia i) {
        String sql = "INSERT INTO incidencia (id_usuario, descripcion, estado) VALUES (?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, i.getIdUsuario());
            ps.setString(2, i.getDescripcion());
            ps.setString(3, i.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar incidencia: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int idIncidencia, String estado) {
        String sql = "UPDATE incidencia SET estado=? WHERE id_incidencia=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idIncidencia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar incidencia: " + e.getMessage());
            return false;
        }
    }

    public List<Incidencia> listarAbiertas() {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidencia WHERE estado='abierto' ORDER BY fecha";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar incidencias: " + e.getMessage());
        }
        return lista;
    }

    public List<Incidencia> listarTodas() {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidencia ORDER BY fecha DESC";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar todas las incidencias: " + e.getMessage());
        }
        return lista;
    }

    private Incidencia mapear(ResultSet rs) throws SQLException {
        Incidencia i = new Incidencia();
        i.setIdIncidencia(rs.getInt("id_incidencia"));
        i.setIdUsuario(rs.getInt("id_usuario"));
        i.setDescripcion(rs.getString("descripcion"));
        i.setEstado(rs.getString("estado"));
        i.setFecha(rs.getDate("fecha"));
        return i;
    }
}
