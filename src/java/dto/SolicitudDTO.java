package bancodiario.dto;

/**
 * SolicitudDTO - Data Transfer Object
 *
 * Transfiere los datos de una solicitud de préstamo hacia la vista
 * del Analista de Crédito. Incluye el nombre del solicitante
 * para evitar JOINs en el JSP.
 */
public class SolicitudDTO {

    private int    idSolicitud;
    private int    idUsuario;
    private String nombreSolicitante;  // nombre del prestatario
    private double monto;
    private int    plazo;
    private double interes;
    private String estado;
    private String fechaSolicitud;

    // Campos de análisis de riesgo (calculados en Service)
    private String nivelRiesgo;   // "Bajo", "Moderado", "Alto"
    private double porcentajeRiesgo;

    public SolicitudDTO() {}

    // ─── Getters y Setters ───────────────────────────────────────────────
    public int    getIdSolicitud()       { return idSolicitud; }
    public void   setIdSolicitud(int v)  { this.idSolicitud = v; }

    public int    getIdUsuario()         { return idUsuario; }
    public void   setIdUsuario(int v)    { this.idUsuario = v; }

    public String getNombreSolicitante()          { return nombreSolicitante; }
    public void   setNombreSolicitante(String v)  { this.nombreSolicitante = v; }

    public double getMonto()            { return monto; }
    public void   setMonto(double v)    { this.monto = v; }

    public int    getPlazo()            { return plazo; }
    public void   setPlazo(int v)       { this.plazo = v; }

    public double getInteres()          { return interes; }
    public void   setInteres(double v)  { this.interes = v; }

    public String getEstado()           { return estado; }
    public void   setEstado(String v)   { this.estado = v; }

    public String getFechaSolicitud()          { return fechaSolicitud; }
    public void   setFechaSolicitud(String v)  { this.fechaSolicitud = v; }

    public String getNivelRiesgo()          { return nivelRiesgo; }
    public void   setNivelRiesgo(String v)  { this.nivelRiesgo = v; }

    public double getPorcentajeRiesgo()         { return porcentajeRiesgo; }
    public void   setPorcentajeRiesgo(double v) { this.porcentajeRiesgo = v; }
}
