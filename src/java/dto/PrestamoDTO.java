package bancodiario.dto;

/**
 * PrestamoDTO - Data Transfer Object
 *
 * Transfiere datos de un préstamo hacia la vista (JSP).
 * Incluye campos calculados (cuotaMensual, totalAPagar) y
 * el montoDB ya listo para mostrar, sin exponer lógica interna.
 */
public class PrestamoDTO {

    private int    idPrestamo;
    private int    idSolicitud;
    private String nombreCliente;   // nombre del prestatario (JOIN con Usuario)
    private double monto;
    private double interes;
    private int    plazo;
    private String estado;
    private String fechaInicio;

    // Campos calculados — no existen en BD, se calculan en el Service
    private double cuotaMensual;
    private double totalAPagar;
    private double totalIntereses;

    // montoDB: total guardado en BD al momento de aprobar el préstamo
    private double montoDB;

    public PrestamoDTO() {}

    // ─── Getters y Setters ───────────────────────────────────────────────
    public int    getIdPrestamo()       { return idPrestamo; }
    public void   setIdPrestamo(int v)  { this.idPrestamo = v; }

    public int    getIdSolicitud()       { return idSolicitud; }
    public void   setIdSolicitud(int v)  { this.idSolicitud = v; }

    public String getNombreCliente()          { return nombreCliente; }
    public void   setNombreCliente(String v)  { this.nombreCliente = v; }

    public double getMonto()            { return monto; }
    public void   setMonto(double v)    { this.monto = v; }

    public double getInteres()          { return interes; }
    public void   setInteres(double v)  { this.interes = v; }

    public int    getPlazo()            { return plazo; }
    public void   setPlazo(int v)       { this.plazo = v; }

    public String getEstado()           { return estado; }
    public void   setEstado(String v)   { this.estado = v; }

    public String getFechaInicio()          { return fechaInicio; }
    public void   setFechaInicio(String v)  { this.fechaInicio = v; }

    public double getCuotaMensual()         { return cuotaMensual; }
    public void   setCuotaMensual(double v) { this.cuotaMensual = v; }

    public double getTotalAPagar()          { return totalAPagar; }
    public void   setTotalAPagar(double v)  { this.totalAPagar = v; }

    public double getTotalIntereses()           { return totalIntereses; }
    public void   setTotalIntereses(double v)   { this.totalIntereses = v; }

    public double getMontoDB()          { return montoDB; }
    public void   setMontoDB(double v)  { this.montoDB = v; }
}
