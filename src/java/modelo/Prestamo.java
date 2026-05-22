package bancodiario.modelo;

import java.util.Date;

public class Prestamo {
    private int idPrestamo;
    private int idSolicitud;
    private double monto;
    private double interes;
    private int plazo;
    private String estado;
    private Date fechaInicio;

    // Monto total calculado y almacenado en BD (capital + intereses)
    private double montoDB;

    public Prestamo() {}

    public Prestamo(int idSolicitud, double monto, double interes, int plazo) {
        this.idSolicitud = idSolicitud;
        this.monto = monto;
        this.interes = interes;
        this.plazo = plazo;
        this.estado = "activo";
        // Calcular y guardar el montoDB automáticamente al crear el préstamo
        double tasaMensual = (interes / 100.0) / 12.0;
        double cuota;
        if (tasaMensual == 0) {
            cuota = monto / plazo;
        } else {
            cuota = monto * tasaMensual / (1 - Math.pow(1 + tasaMensual, -plazo));
        }
        this.montoDB = cuota * plazo;
    }

    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public double getInteres() { return interes; }
    public void setInteres(double interes) { this.interes = interes; }

    public int getPlazo() { return plazo; }
    public void setPlazo(int plazo) { this.plazo = plazo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public double getMontoDB() { return montoDB; }
    public void setMontoDB(double montoDB) { this.montoDB = montoDB; }

    public double calcularCuotaMensual() {
        double tasaMensual = (interes / 100.0) / 12.0;
        if (tasaMensual == 0) return monto / plazo;
        return monto * tasaMensual / (1 - Math.pow(1 + tasaMensual, -plazo));
    }

    public double calcularTotalAPagar() {
        return calcularCuotaMensual() * plazo;
    }
}
