package bancodiario.modelo;

import java.util.Date;

public class Solicitud {
    private int idSolicitud;
    private int idUsuario;
    private double monto;
    private int plazo;
    private double interes;
    private String estado;
    private Date fechaSolicitud;

    public Solicitud() {}

    public Solicitud(int idUsuario, double monto, int plazo, double interes) {
        this.idUsuario = idUsuario;
        this.monto = monto;
        this.plazo = plazo;
        this.interes = interes;
        this.estado = "pendiente";
    }

    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public int getPlazo() { return plazo; }
    public void setPlazo(int plazo) { this.plazo = plazo; }

    public double getInteres() { return interes; }
    public void setInteres(double interes) { this.interes = interes; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
}
