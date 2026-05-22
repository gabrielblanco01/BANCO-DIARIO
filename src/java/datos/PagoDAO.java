package bancodiario.datos;

import bancodiario.modelo.Pago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    private Connection con;

    public PagoDAO() {
        this.con = Conexion.getConexion();
    }

    public boolean insertar(Pago p) {
        String sql = "INSERT INTO pago (id_prestamo, monto, estado) VALUES (?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdPrestamo());
            ps.setDouble(2, p.getMonto());
            ps.setString(3, p.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insertar pago: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int idPago, String estado) {
        String sql = "UPDATE pago SET estado=? WHERE id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idPago);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar pago: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idPago) {
        String sql = "DELETE FROM pago WHERE id_pago=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPago);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar pago: " + e.getMessage());
            return false;
        }
    }

    public List<Pago> listarPorPrestamo(int idPrestamo) {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago WHERE id_prestamo=? ORDER BY fecha_pago";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar pagos: " + e.getMessage());
        }
        return lista;
    }

    public List<Pago> listarTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago ORDER BY fecha_pago DESC";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error listar todos los pagos: " + e.getMessage());
        }
        return lista;
    }

    private Pago mapear(ResultSet rs) throws SQLException {
        Pago p = new Pago();
        p.setIdPago(rs.getInt("id_pago"));
        p.setIdPrestamo(rs.getInt("id_prestamo"));
        p.setMonto(rs.getDouble("monto"));
        p.setFechaPago(rs.getDate("fecha_pago"));
        p.setEstado(rs.getString("estado"));
        return p;
    }
}
