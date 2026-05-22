package bancodiario.dto;

/**
 * PagoDTO - Data Transfer Object
 *
 * Transfiere datos de un pago hacia la vista.
 * Incluye el nombre del prestatario y el saldo pendiente
 * calculado, evitando lógica en el JSP.
 */
public class PagoDTO {

    private int    idPago;
    private int    idPrestamo;
    private String nombrePrestatario;  // para mostrar en tabla sin JOIN en JSP
    private double monto;
    private String fechaPago;
    private String estado;

    // Campo calculado: saldo que quedó pendiente después de este pago
    private double saldoPendiente;

    public PagoDTO() {}

    // ─── Getters y Setters ───────────────────────────────────────────────
    public int    getIdPago()           { return idPago; }
    public void   setIdPago(int v)      { this.idPago = v; }

    public int    getIdPrestamo()       { return idPrestamo; }
    public void   setIdPrestamo(int v)  { this.idPrestamo = v; }

    public String getNombrePrestatario()          { return nombrePrestatario; }
    public void   setNombrePrestatario(String v)  { this.nombrePrestatario = v; }

    public double getMonto()            { return monto; }
    public void   setMonto(double v)    { this.monto = v; }

    public String getFechaPago()          { return fechaPago; }
    public void   setFechaPago(String v)  { this.fechaPago = v; }

    public String getEstado()           { return estado; }
    public void   setEstado(String v)   { this.estado = v; }

    public double getSaldoPendiente()         { return saldoPendiente; }
    public void   setSaldoPendiente(double v) { this.saldoPendiente = v; }
}
