package bancodiario.dto;

/**
 * CobranzaDTO - Data Transfer Object
 *
 * Transfiere datos de gestión de cobranza hacia la vista
 * del Agente de Cobranza. Incluye nombre del deudor y
 * datos del préstamo vencido para no hacer lógica en el JSP.
 */
public class CobranzaDTO {

    private int    idCobranza;
    private int    idPrestamo;
    private String nombreDeudor;       // nombre del prestatario moroso
    private double montoDeuda;         // monto total del préstamo vencido
    private String estado;             // "abierto", "cerrado"
    private String fecha;
    private String comentario;

    // Datos de contacto registrado
    private String tipoContacto;       // "Llamada", "Visita", "Mensaje"
    private String resultadoContacto;

    public CobranzaDTO() {}

    // ─── Getters y Setters ───────────────────────────────────────────────
    public int    getIdCobranza()       { return idCobranza; }
    public void   setIdCobranza(int v)  { this.idCobranza = v; }

    public int    getIdPrestamo()       { return idPrestamo; }
    public void   setIdPrestamo(int v)  { this.idPrestamo = v; }

    public String getNombreDeudor()          { return nombreDeudor; }
    public void   setNombreDeudor(String v)  { this.nombreDeudor = v; }

    public double getMontoDeuda()           { return montoDeuda; }
    public void   setMontoDeuda(double v)   { this.montoDeuda = v; }

    public String getEstado()           { return estado; }
    public void   setEstado(String v)   { this.estado = v; }

    public String getFecha()            { return fecha; }
    public void   setFecha(String v)    { this.fecha = v; }

    public String getComentario()           { return comentario; }
    public void   setComentario(String v)   { this.comentario = v; }

    public String getTipoContacto()          { return tipoContacto; }
    public void   setTipoContacto(String v)  { this.tipoContacto = v; }

    public String getResultadoContacto()          { return resultadoContacto; }
    public void   setResultadoContacto(String v)  { this.resultadoContacto = v; }
}
