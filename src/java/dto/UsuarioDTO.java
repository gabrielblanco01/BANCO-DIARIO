package bancodiario.dto;

/**
 * UsuarioDTO - Data Transfer Object
 *
 * Transfiere datos del usuario entre capas SIN exponer
 * información sensible como la contraseña.
 * Lo que llega al JSP es este objeto, no el modelo Usuario.
 */
public class UsuarioDTO {

    private int    idUsuario;
    private String nombre;
    private String email;
    private String telefono;
    private String estado;
    private String nombreRol;   // "Administrador", "Prestamista", etc.
    private String fechaRegistro;

    public UsuarioDTO() {}

    public UsuarioDTO(int idUsuario, String nombre, String email,
                      String telefono, String estado, String nombreRol) {
        this.idUsuario  = idUsuario;
        this.nombre     = nombre;
        this.email      = email;
        this.telefono   = telefono;
        this.estado     = estado;
        this.nombreRol  = nombreRol;
    }

    // ─── Getters y Setters ───────────────────────────────────────────────
    public int    getIdUsuario()      { return idUsuario; }
    public void   setIdUsuario(int v) { this.idUsuario = v; }

    public String getNombre()         { return nombre; }
    public void   setNombre(String v) { this.nombre = v; }

    public String getEmail()          { return email; }
    public void   setEmail(String v)  { this.email = v; }

    public String getTelefono()           { return telefono; }
    public void   setTelefono(String v)   { this.telefono = v; }

    public String getEstado()             { return estado; }
    public void   setEstado(String v)     { this.estado = v; }

    public String getNombreRol()          { return nombreRol; }
    public void   setNombreRol(String v)  { this.nombreRol = v; }

    public String getFechaRegistro()          { return fechaRegistro; }
    public void   setFechaRegistro(String v)  { this.fechaRegistro = v; }
}
