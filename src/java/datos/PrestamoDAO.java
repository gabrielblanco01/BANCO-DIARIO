package bancodiario.datos;

import bancodiario.modelo.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private Connection con;

    public PrestamoDAO() {
        this.con = Conexion.getConexion();
    }

    public boolean insertar(Prestamo p) {
        String sql = "INSERT INTO prestamo (id_solicitud, monto, interes, plazo, estado) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdSolicitud());
            ps.setDouble(2, p.getMonto());
            ps.setDouble(3, p.getInteres());
            ps.setInt(4, p.getPlazo());
            ps.setString(5, p.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar prestamo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int idPrestamo, String estado) {
        String sql = "UPDATE prestamo SET estado=? WHERE id_prestamo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idPrestamo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar estado prestamo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idPrestamo) {
        String sql = "DELETE FROM prestamo WHERE id_prestamo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar prestamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> listarTodos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamo ORDER BY id_prestamo";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar prestamos: " + e.getMessage());
        }
        return lista;
    }

    public List<Prestamo> listarPorEstado(String estado) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamo WHERE estado=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar por estado: " + e.getMessage());
        }
        return lista;
    }

    public Prestamo buscarPorId(int id) {
        String sql = "SELECT * FROM prestamo WHERE id_prestamo=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error buscar prestamo: " + e.getMessage());
        }
        return null;
    }

    private Prestamo mapear(ResultSet rs) throws SQLException {
        Prestamo p = new Prestamo();
        p.setIdPrestamo(rs.getInt("id_prestamo"));
        p.setIdSolicitud(rs.getInt("id_solicitud"));
        p.setMonto(rs.getDouble("monto"));
        p.setInteres(rs.getDouble("interes"));
        p.setPlazo(rs.getInt("plazo"));
        p.setEstado(rs.getString("estado"));
        p.setFechaInicio(rs.getDate("fecha_inicio"));
        return p;
    }
}
