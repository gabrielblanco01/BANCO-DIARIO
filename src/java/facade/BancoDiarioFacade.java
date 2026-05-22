package bancodiario.facade;

import bancodiario.logica.PrestamoService;
import bancodiario.logica.PagoService;
import bancodiario.logica.UsuarioService;
import bancodiario.logica.SoporteService;
import bancodiario.modelo.Prestamo;
import bancodiario.modelo.Pago;
import bancodiario.modelo.Usuario;
import bancodiario.modelo.Solicitud;
import bancodiario.modelo.Cobranza;
import bancodiario.modelo.Incidencia;
import java.util.List;

/**
 * BancoDiarioFacade - Patrón de Diseño Facade
 *
 * Proporciona una interfaz simplificada para el sistema Banco Diario,
 * coordinando internamente los servicios: PrestamoService, PagoService,
 * UsuarioService y SoporteService.
 *
 * Los Servlets (capa de presentación) solo interactúan con esta clase,
 * sin conocer la complejidad interna del sistema.
 *
 * Proyecto: Banco Diario - UAC
 * Docente: Carlos Henríquez
 */
public class BancoDiarioFacade {

    // ─── Services internos (ocultos para la capa de presentación) ───────────
    private final PrestamoService prestamoService;
    private final PagoService     pagoService;
    private final UsuarioService  usuarioService;
    private final SoporteService  soporteService;

    // ─── Singleton: una sola instancia del Facade en toda la aplicación ──────
    private static BancoDiarioFacade instancia;

    private BancoDiarioFacade() {
        this.prestamoService = new PrestamoService();
        this.pagoService     = new PagoService();
        this.usuarioService  = new UsuarioService();
        this.soporteService  = new SoporteService();
    }

    public static synchronized BancoDiarioFacade getInstancia() {
        if (instancia == null) {
            instancia = new BancoDiarioFacade();
        }
        return instancia;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÓDULO DE USUARIOS
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Registra un nuevo usuario en el sistema.
     */
    public boolean registrarUsuario(String nombre, String email,
                                    String contrasena, String telefono, int idRol) {
        return usuarioService.registrar(nombre, email, contrasena, telefono, idRol);
    }

    /**
     * Autentica un usuario por email y contraseña.
     * Retorna el objeto Usuario si es válido, null si falla.
     */
    public Usuario autenticarUsuario(String email, String contrasena) {
        return usuarioService.autenticar(email, contrasena);
    }

    /**
     * Obtiene todos los usuarios registrados.
     */
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    /**
     * Elimina un usuario por su ID.
     */
    public boolean eliminarUsuario(int idUsuario) {
        return usuarioService.eliminar(idUsuario);
    }

    /**
     * Actualiza los datos de un usuario.
     */
    public boolean actualizarUsuario(Usuario u) {
        return usuarioService.actualizar(u);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÓDULO DE PRÉSTAMOS
    // ════════════════════════════════════════════════════════════════════════

    /**
     * El prestatario solicita un nuevo préstamo.
     * Valida monto (100.000 - 2.000.000 COP) y plazo (1-24 meses).
     */
    public boolean solicitarPrestamo(int idUsuario, double monto,
                                     int plazo, double interes) {
        return prestamoService.solicitarPrestamo(idUsuario, monto, plazo, interes);
    }

    /**
     * El analista aprueba una solicitud → se genera el préstamo.
     * El campo montoDB se calcula automáticamente en el modelo Prestamo.
     */
    public boolean aprobarSolicitud(int idSolicitud) {
        return prestamoService.aprobarSolicitud(idSolicitud);
    }

    /**
     * El analista rechaza una solicitud de préstamo.
     */
    public boolean rechazarSolicitud(int idSolicitud) {
        return prestamoService.rechazarSolicitud(idSolicitud);
    }

    /**
     * Obtiene todas las solicitudes pendientes de evaluación.
     */
    public List<Solicitud> listarSolicitudesPendientes() {
        return prestamoService.obtenerSolicitudesPendientes();
    }

    /**
     * Obtiene las solicitudes de un prestatario específico.
     */
    public List<Solicitud> listarSolicitudesDeUsuario(int idUsuario) {
        return prestamoService.obtenerSolicitudesDeUsuario(idUsuario);
    }

    /**
     * Lista todos los préstamos del sistema.
     */
    public List<Prestamo> listarPrestamos() {
        return prestamoService.obtenerTodos();
    }

    /**
     * Lista préstamos filtrados por estado (activo, pagado, vencido, etc.).
     */
    public List<Prestamo> listarPrestamosPorEstado(String estado) {
        return prestamoService.obtenerPorEstado(estado);
    }

    /**
     * Obtiene un préstamo por su ID.
     */
    public Prestamo obtenerPrestamo(int idPrestamo) {
        return prestamoService.obtenerPorId(idPrestamo);
    }

    /**
     * Actualiza el estado de un préstamo (activo, pagado, vencido).
     */
    public boolean actualizarEstadoPrestamo(int idPrestamo, String estado) {
        return prestamoService.actualizarEstado(idPrestamo, estado);
    }

    /**
     * Elimina un préstamo del sistema.
     */
    public boolean eliminarPrestamo(int idPrestamo) {
        return prestamoService.eliminar(idPrestamo);
    }

    /**
     * Simula el costo de un crédito sin guardarlo en BD.
     * Retorna: [cuotaMensual, totalAPagar, totalIntereses]
     */
    public double[] simularCredito(double monto, double tasaAnual, int plazoMeses) {
        return prestamoService.simularCredito(monto, tasaAnual, plazoMeses);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÓDULO DE PAGOS
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Registra un pago realizado sobre un préstamo activo.
     */
    public boolean registrarPago(int idPrestamo, double monto) {
        return pagoService.registrarPago(idPrestamo, monto);
    }

    /**
     * Lista todos los pagos de un préstamo específico.
     */
    public List<Pago> listarPagosDePrestamo(int idPrestamo) {
        return pagoService.obtenerPagosDePrestamo(idPrestamo);
    }

    /**
     * Lista todos los pagos registrados en el sistema.
     */
    public List<Pago> listarTodosPagos() {
        return pagoService.obtenerTodos();
    }

    /**
     * Elimina un pago por su ID.
     */
    public boolean eliminarPago(int idPago) {
        return pagoService.eliminarPago(idPago);
    }

    /**
     * Actualiza el estado de un pago.
     */
    public boolean actualizarEstadoPago(int idPago, String estado) {
        return pagoService.actualizarEstado(idPago, estado);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÓDULO DE COBRANZA
    // ════════════════════════════════════════════════════════════════════════

    /**
     * El agente registra una gestión de cobranza sobre un préstamo vencido.
     */
    public boolean registrarCobranza(int idPrestamo, int idAgente, String comentario) {
        return soporteService.registrarCobranza(idPrestamo, idAgente, comentario);
    }

    /**
     * Cierra una cobranza gestionada.
     */
    public boolean cerrarCobranza(int idCobranza) {
        return soporteService.cerrarCobranza(idCobranza);
    }

    /**
     * Lista las cobranzas abiertas (préstamos vencidos en gestión).
     */
    public List<Cobranza> listarCobranzasAbiertas() {
        return soporteService.obtenerCobranzasAbiertas();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÓDULO DE SOPORTE TÉCNICO
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Un usuario reporta una incidencia técnica al sistema.
     */
    public boolean reportarIncidencia(int idUsuario, String descripcion) {
        return soporteService.reportarIncidencia(idUsuario, descripcion);
    }

    /**
     * El soporte técnico cierra una incidencia resuelta.
     */
    public boolean cerrarIncidencia(int idIncidencia) {
        return soporteService.cerrarIncidencia(idIncidencia);
    }

    /**
     * Lista las incidencias abiertas o en proceso.
     */
    public List<Incidencia> listarIncidenciasAbiertas() {
        return soporteService.obtenerIncidenciasAbiertas();
    }

    /**
     * Lista el historial completo de incidencias.
     */
    public List<Incidencia> listarTodasIncidencias() {
        return soporteService.obtenerTodasIncidencias();
    }
}
