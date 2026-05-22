package bancodiario.logica;

import bancodiario.datos.PrestamoDAO;
import bancodiario.datos.SolicitudDAO;
import bancodiario.modelo.Prestamo;
import bancodiario.modelo.Solicitud;
import java.util.List;

public class PrestamoService {

    private PrestamoDAO prestamoDAO;
    private SolicitudDAO solicitudDAO;

    public PrestamoService() {
        this.prestamoDAO = new PrestamoDAO();
        this.solicitudDAO = new SolicitudDAO();
    }

    // El prestatario solicita un préstamo
    public boolean solicitarPrestamo(int idUsuario, double monto, int plazo, double interes) {
        if (monto < 100000 || monto > 2000000) {
            System.err.println("Monto fuera del rango permitido (100.000 - 2.000.000 COP)");
            return false;
        }
        if (plazo <= 0 || plazo > 24) {
            System.err.println("Plazo inválido (1-24 meses)");
            return false;
        }
        Solicitud s = new Solicitud(idUsuario, monto, plazo, interes);
        return solicitudDAO.insertar(s);
    }

    // El analista aprueba la solicitud → se crea el préstamo
    public boolean aprobarSolicitud(int idSolicitud) {
        Solicitud s = solicitudDAO.buscarPorId(idSolicitud);
        if (s == null) return false;
        solicitudDAO.actualizarEstado(idSolicitud, "aprobado");
        Prestamo p = new Prestamo(idSolicitud, s.getMonto(), s.getInteres(), s.getPlazo());
        return prestamoDAO.insertar(p);
    }

    // El analista rechaza la solicitud
    public boolean rechazarSolicitud(int idSolicitud) {
        return solicitudDAO.actualizarEstado(idSolicitud, "rechazado");
    }

    public boolean actualizarEstado(int idPrestamo, String estado) {
        return prestamoDAO.actualizarEstado(idPrestamo, estado);
    }

    public boolean eliminar(int idPrestamo) {
        return prestamoDAO.eliminar(idPrestamo);
    }

    public List<Prestamo> obtenerTodos() {
        return prestamoDAO.listarTodos();
    }

    public List<Prestamo> obtenerPorEstado(String estado) {
        return prestamoDAO.listarPorEstado(estado);
    }

    public List<Solicitud> obtenerSolicitudesPendientes() {
        return solicitudDAO.listarPendientes();
    }

    public List<Solicitud> obtenerSolicitudesDeUsuario(int idUsuario) {
        return solicitudDAO.listarPorUsuario(idUsuario);
    }

    public Prestamo obtenerPorId(int id) {
        return prestamoDAO.buscarPorId(id);
    }

    // Simular crédito sin guardar en BD
    public double[] simularCredito(double monto, double tasaAnual, int plazoMeses) {
        double tasaMensual = (tasaAnual / 100.0) / 12.0;
        double cuota;
        if (tasaMensual == 0) {
            cuota = monto / plazoMeses;
        } else {
            cuota = monto * tasaMensual / (1 - Math.pow(1 + tasaMensual, -plazoMeses));
        }
        double total = cuota * plazoMeses;
        double interesPagado = total - monto;
        return new double[]{cuota, total, interesPagado};
    }
}
