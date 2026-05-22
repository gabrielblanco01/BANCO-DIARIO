package bancodiario.datos;

import bancodiario.modelo.Solicitud;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {

    private Connection con;

    public SolicitudDAO() {
        this.con = Conexion.getConexion();
    }

    public boolean insertar(Solicitud s) {
        String sql = "INSERT INTO solicitud (id_usuario, monto, plazo, interes, estado) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getIdUsuario());
            ps.setDouble(2, s.getMonto());
            ps.setInt(3, s.getPlazo());
            ps.setDouble(4, s.getInteres());
            ps.setString(5, s.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar solicitud: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int idSolicitud, String estado) {
        String sql = "UPDATE solicitud SET estado=? WHERE id_solicitud=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idSolicitud);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar solicitud: " + e.getMessage());
            return false;
        }
    }

    public List<Solicitud> listarPendientes() {
        List<Solicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud WHERE estado='pendiente' ORDER BY fecha_solicitud";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar solicitudes pendientes: " + e.getMessage());
        }
        return lista;
    }

    public List<Solicitud> listarPorUsuario(int idUsuario) {
        List<Solicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud WHERE id_usuario=? ORDER BY fecha_solicitud DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar solicitudes por usuario: " + e.getMessage());
        }
        return lista;
    }

    public Solicitud buscarPorId(int id) {
        String sql = "SELECT * FROM solicitud WHERE id_solicitud=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error buscar solicitud: " + e.getMessage());
        }
        return null;
    }

    private Solicitud mapear(ResultSet rs) throws SQLException {
        Solicitud s = new Solicitud();
        s.setIdSolicitud(rs.getInt("id_solicitud"));
        s.setIdUsuario(rs.getInt("id_usuario"));
        s.setMonto(rs.getDouble("monto"));
        s.setPlazo(rs.getInt("plazo"));
        s.setInteres(rs.getDouble("interes"));
        s.setEstado(rs.getString("estado"));
        s.setFechaSolicitud(rs.getDate("fecha_solicitud"));
        return s;
    }
}
