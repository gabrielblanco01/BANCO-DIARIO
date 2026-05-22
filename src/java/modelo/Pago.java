package bancodiario.modelo;

import java.util.Date;

public class Pago {
    private int idPago;
    private int idPrestamo;
    private double monto;
    private Date fechaPago;
    private String estado;

    public Pago() {}

    public Pago(int idPrestamo, double monto) {
        this.idPrestamo = idPrestamo;
        this.monto = monto;
        this.estado = "pendiente";
    }

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
