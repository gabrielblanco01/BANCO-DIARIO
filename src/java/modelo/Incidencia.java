package bancodiario.modelo;

import java.util.Date;

public class Incidencia {
    private int idIncidencia;
    private int idUsuario;
    private String descripcion;
    private String estado;
    private Date fecha;

    public Incidencia() {}

    public Incidencia(int idUsuario, String descripcion) {
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.estado = "abierto";
    }

    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
