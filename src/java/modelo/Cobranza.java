package bancodiario.modelo;

import java.util.Date;

public class Cobranza {
    private int idCobranza;
    private int idPrestamo;
    private int idUsuario;
    private String estado;
    private Date fecha;
    private String comentario;

    public Cobranza() {}

    public Cobranza(int idPrestamo, int idUsuario, String comentario) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.comentario = comentario;
        this.estado = "abierto";
    }

    public int getIdCobranza() { return idCobranza; }
    public void setIdCobranza(int idCobranza) { this.idCobranza = idCobranza; }

    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
