package bancodiario.dto;

import bancodiario.modelo.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * DTOMapper - Convertidor de Modelos a DTOs
 *
 * Centraliza toda la lógica de conversión entre el modelo de datos
 * (entidades de BD) y los DTOs (objetos para la vista).
 *
 * Los Servlets y JSPs nunca trabajan con los modelos directamente;
 * siempre reciben DTOs listos para mostrar.
 */
public class DTOMapper {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    // ────────────────────────────────────────────────────────────────────
    //  USUARIO
    // ────────────────────────────────────────────────────────────────────

    public static UsuarioDTO toUsuarioDTO(Usuario u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNombre(u.getNombre());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        dto.setEstado(u.getEstado());
        dto.setNombreRol(resolverNombreRol(u.getIdRol()));
        if (u.getFechaRegistro() != null)
            dto.setFechaRegistro(SDF.format(u.getFechaRegistro()));
        // La contraseña NO se mapea al DTO — seguridad
        return dto;
    }

    public static List<UsuarioDTO> toUsuarioDTOList(List<Usuario> lista) {
        List<UsuarioDTO> result = new ArrayList<>();
        if (lista != null)
            for (Usuario u : lista) result.add(toUsuarioDTO(u));
        return result;
    }

    // ────────────────────────────────────────────────────────────────────
    //  PRÉSTAMO
    // ────────────────────────────────────────────────────────────────────

    public static PrestamoDTO toPrestamoDTO(Prestamo p) {
        if (p == null) return null;
        PrestamoDTO dto = new PrestamoDTO();
        dto.setIdPrestamo(p.getIdPrestamo());
        dto.setIdSolicitud(p.getIdSolicitud());
        dto.setMonto(p.getMonto());
        dto.setInteres(p.getInteres());
        dto.setPlazo(p.getPlazo());
        dto.setEstado(p.getEstado());
        dto.setMontoDB(p.getMontoDB());
        if (p.getFechaInicio() != null)
            dto.setFechaInicio(SDF.format(p.getFechaInicio()));
        // Campos calculados
        dto.setCuotaMensual(p.calcularCuotaMensual());
        dto.setTotalAPagar(p.calcularTotalAPagar());
        dto.setTotalIntereses(p.calcularTotalAPagar() - p.getMonto());
        return dto;
    }

    public static List<PrestamoDTO> toPrestamosDTOList(List<Prestamo> lista) {
        List<PrestamoDTO> result = new ArrayList<>();
        if (lista != null)
            for (Prestamo p : lista) result.add(toPrestamoDTO(p));
        return result;
    }

    // ────────────────────────────────────────────────────────────────────
    //  SOLICITUD
    // ────────────────────────────────────────────────────────────────────

    public static SolicitudDTO toSolicitudDTO(Solicitud s) {
        if (s == null) return null;
        SolicitudDTO dto = new SolicitudDTO();
        dto.setIdSolicitud(s.getIdSolicitud());
        dto.setIdUsuario(s.getIdUsuario());
        dto.setMonto(s.getMonto());
        dto.setPlazo(s.getPlazo());
        dto.setInteres(s.getInteres());
        dto.setEstado(s.getEstado());
        if (s.getFechaSolicitud() != null)
            dto.setFechaSolicitud(SDF.format(s.getFechaSolicitud()));
        // Nivel de riesgo calculado según monto
        dto.setNivelRiesgo(calcularNivelRiesgo(s.getMonto()));
        dto.setPorcentajeRiesgo(calcularPorcentajeRiesgo(s.getMonto()));
        return dto;
    }

    public static List<SolicitudDTO> toSolicitudDTOList(List<Solicitud> lista) {
        List<SolicitudDTO> result = new ArrayList<>();
        if (lista != null)
            for (Solicitud s : lista) result.add(toSolicitudDTO(s));
        return result;
    }

    // ────────────────────────────────────────────────────────────────────
    //  PAGO
    // ────────────────────────────────────────────────────────────────────

    public static PagoDTO toPagoDTO(Pago p) {
        if (p == null) return null;
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(p.getIdPago());
        dto.setIdPrestamo(p.getIdPrestamo());
        dto.setMonto(p.getMonto());
        dto.setEstado(p.getEstado());
        if (p.getFechaPago() != null)
            dto.setFechaPago(SDF.format(p.getFechaPago()));
        return dto;
    }

    public static List<PagoDTO> toPagoDTOList(List<Pago> lista) {
        List<PagoDTO> result = new ArrayList<>();
        if (lista != null)
            for (Pago p : lista) result.add(toPagoDTO(p));
        return result;
    }

    // ────────────────────────────────────────────────────────────────────
    //  COBRANZA
    // ────────────────────────────────────────────────────────────────────

    public static CobranzaDTO toCobranzaDTO(Cobranza c) {
        if (c == null) return null;
        CobranzaDTO dto = new CobranzaDTO();
        dto.setIdCobranza(c.getIdCobranza());
        dto.setIdPrestamo(c.getIdPrestamo());
        dto.setEstado(c.getEstado());
        dto.setComentario(c.getComentario());
        if (c.getFecha() != null)
            dto.setFecha(SDF.format(c.getFecha()));
        return dto;
    }

    public static List<CobranzaDTO> toCobranzaDTOList(List<Cobranza> lista) {
        List<CobranzaDTO> result = new ArrayList<>();
        if (lista != null)
            for (Cobranza c : lista) result.add(toCobranzaDTO(c));
        return result;
    }

    // ────────────────────────────────────────────────────────────────────
    //  INCIDENCIA
    // ────────────────────────────────────────────────────────────────────

    public static IncidenciaDTO toIncidenciaDTO(Incidencia i) {
        if (i == null) return null;
        IncidenciaDTO dto = new IncidenciaDTO();
        dto.setIdIncidencia(i.getIdIncidencia());
        dto.setIdUsuario(i.getIdUsuario());
        dto.setDescripcion(i.getDescripcion());
        dto.setEstado(i.getEstado());
        dto.setPrioridad(calcularPrioridad(i.getDescripcion()));
        if (i.getFecha() != null)
            dto.setFecha(SDF.format(i.getFecha()));
        return dto;
    }

    public static List<IncidenciaDTO> toIncidenciaDTOList(List<Incidencia> lista) {
        List<IncidenciaDTO> result = new ArrayList<>();
        if (lista != null)
            for (Incidencia i : lista) result.add(toIncidenciaDTO(i));
        return result;
    }

    // ────────────────────────────────────────────────────────────────────
    //  MÉTODOS AUXILIARES PRIVADOS
    // ────────────────────────────────────────────────────────────────────

    private static String resolverNombreRol(int idRol) {
        switch (idRol) {
            case 1: return "Administrador";
            case 2: return "Prestamista";
            case 3: return "Prestatario";
            case 4: return "Analista de Crédito";
            case 5: return "Agente de Cobranza";
            case 6: return "Soporte Técnico";
            default: return "Desconocido";
        }
    }

    private static String calcularNivelRiesgo(double monto) {
        if (monto <= 500000)  return "Bajo";
        if (monto <= 1200000) return "Moderado";
        return "Alto";
    }

    private static double calcularPorcentajeRiesgo(double monto) {
        if (monto <= 500000)  return 15.0;
        if (monto <= 1200000) return 35.0;
        return 70.0;
    }

    private static String calcularPrioridad(String descripcion) {
        if (descripcion == null) return "Baja";
        String desc = descripcion.toLowerCase();
        if (desc.contains("error") || desc.contains("fallo") || desc.contains("no carga"))
            return "Alta";
        if (desc.contains("lento") || desc.contains("demora") || desc.contains("tarda"))
            return "Media";
        return "Baja";
    }
}
