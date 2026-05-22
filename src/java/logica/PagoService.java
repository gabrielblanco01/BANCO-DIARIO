package bancodiario.logica;

import bancodiario.datos.PagoDAO;
import bancodiario.datos.PrestamoDAO;
import bancodiario.modelo.Pago;
import java.util.List;

public class PagoService {

    private PagoDAO pagoDAO;
    private PrestamoDAO prestamoDAO;

    public PagoService() {
        this.pagoDAO = new PagoDAO();
        this.prestamoDAO = new PrestamoDAO();
    }

    public boolean registrarPago(int idPrestamo, double monto) {
        if (monto <= 0) return false;
        Pago p = new Pago(idPrestamo, monto);
        p.setEstado("pagado");
        return pagoDAO.insertar(p);
    }

    public boolean actualizarEstado(int idPago, String estado) {
        return pagoDAO.actualizarEstado(idPago, estado);
    }

    public boolean eliminarPago(int idPago) {
        return pagoDAO.eliminar(idPago);
    }

    public List<Pago> obtenerPagosDePrestamo(int idPrestamo) {
        return pagoDAO.listarPorPrestamo(idPrestamo);
    }

    public List<Pago> obtenerTodos() {
        return pagoDAO.listarTodos();
    }
}
