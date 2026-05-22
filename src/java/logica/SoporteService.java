package bancodiario.logica;

import bancodiario.datos.CobranzaDAO;
import bancodiario.datos.IncidenciaDAO;
import bancodiario.modelo.Cobranza;
import bancodiario.modelo.Incidencia;
import java.util.List;

public class SoporteService {

    private CobranzaDAO cobranzaDAO;
    private IncidenciaDAO incidenciaDAO;

    public SoporteService() {
        this.cobranzaDAO = new CobranzaDAO();
        this.incidenciaDAO = new IncidenciaDAO();
    }

    // --- Cobranza ---
    public boolean registrarCobranza(int idPrestamo, int idAgente, String comentario) {
        Cobranza c = new Cobranza(idPrestamo, idAgente, comentario);
        return cobranzaDAO.insertar(c);
    }

    public boolean cerrarCobranza(int idCobranza) {
        return cobranzaDAO.actualizarEstado(idCobranza, "cerrado");
    }

    public List<Cobranza> obtenerCobranzasAbiertas() {
        return cobranzaDAO.listarAbiertas();
    }

    // --- Incidencias ---
    public boolean reportarIncidencia(int idUsuario, String descripcion) {
        Incidencia i = new Incidencia(idUsuario, descripcion);
        return incidenciaDAO.insertar(i);
    }

    public boolean cerrarIncidencia(int idIncidencia) {
        return incidenciaDAO.actualizarEstado(idIncidencia, "cerrado");
    }

    public List<Incidencia> obtenerIncidenciasAbiertas() {
        return incidenciaDAO.listarAbiertas();
    }

    public List<Incidencia> obtenerTodasIncidencias() {
        return incidenciaDAO.listarTodas();
    }
}
