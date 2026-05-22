package bancodiario.modelo;

import java.util.Date;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
    private String estado;
    private Date fechaRegistro;
    private int idRol;

    public Usuario() {}

    public Usuario(String nombre, String email, String contrasena, String telefono, int idRol) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.idRol = idRol;
        this.estado = "activo";
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}
