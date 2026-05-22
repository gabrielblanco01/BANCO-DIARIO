package bancodiario.dto;

/**
 * IncidenciaDTO - Data Transfer Object
 *
 * Transfiere datos de incidencias técnicas hacia la vista
 * del Soporte Técnico. Incluye el nombre del usuario que
 * reportó y la prioridad calculada.
 */
public class IncidenciaDTO {

    private int    idIncidencia;
    private int    idUsuario;
    private String nombreUsuario;      // quien reportó el problema
    private String descripcion;
    private String estado;             // "abierto", "en proceso", "cerrado"
    private String fecha;

    // Campo calculado según tipo de error
    private String prioridad;          // "Alta", "Media", "Baja"

    public IncidenciaDTO() {}

    // ─── Getters y Setters ───────────────────────────────────────────────
    public int    getIdIncidencia()       { return idIncidencia; }
    public void   setIdIncidencia(int v)  { this.idIncidencia = v; }

    public int    getIdUsuario()          { return idUsuario; }
    public void   setIdUsuario(int v)     { this.idUsuario = v; }

    public String getNombreUsuario()          { return nombreUsuario; }
    public void   setNombreUsuario(String v)  { this.nombreUsuario = v; }

    public String getDescripcion()            { return descripcion; }
    public void   setDescripcion(String v)    { this.descripcion = v; }

    public String getEstado()             { return estado; }
    public void   setEstado(String v)     { this.estado = v; }

    public String getFecha()              { return fecha; }
    public void   setFecha(String v)      { this.fecha = v; }

    public String getPrioridad()          { return prioridad; }
    public void   setPrioridad(String v)  { this.prioridad = v; }
}
