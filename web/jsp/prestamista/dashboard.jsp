<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bancodiario.modelo.*, java.util.List" %>
<%
    Usuario sesion = (Usuario) session.getAttribute("usuario");
    if (sesion == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Prestamo> prestamos = (List<Prestamo>) request.getAttribute("prestamos");
    List<Pago> pagos = (List<Pago>) request.getAttribute("pagos");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Banco Diario - Prestamista</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="brand">💰 Banco Diario</span>
    <div class="user-info">
        <span>👤 <%= sesion.getNombre() %> | Prestamista</span>
        <form method="get" action="<%= request.getContextPath() %>/logout" style="display:inline">
            <button type="submit" class="btn-logout">Cerrar sesión</button>
        </form>
    </div>
</nav>

<div class="container">
    <div class="page-title">Panel Prestamista</div>

    <% if ("eliminado".equals(msg)) { %><div class="alert alert-info">🗑️ Préstamo eliminado.</div><% } %>

    <div class="tabs">
        <button class="tab-btn active" onclick="mostrarTab('prestamos')">Préstamos</button>
        <button class="tab-btn" onclick="mostrarTab('pagos')">Pagos</button>
    </div>

    <div id="tab-prestamos" class="tab-content active">
        <div class="card">
            <h3>📊 Gestión de Préstamos</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID</th><th>Solicitud</th><th>Monto</th><th>Interés</th><th>Plazo</th><th>Estado</th><th>Fecha</th><th>Acción</th></tr></thead>
                    <tbody>
                    <% if (prestamos != null) for (Prestamo p : prestamos) { %>
                        <tr>
                            <td><%= p.getIdPrestamo() %></td>
                            <td><%= p.getIdSolicitud() %></td>
                            <td>$<%= String.format("%,.0f", p.getMonto()) %></td>
                            <td><%= p.getInteres() %>%</td>
                            <td><%= p.getPlazo() %> meses</td>
                            <td>
                                <span class="badge <%=
                                    "activo".equals(p.getEstado()) ? "badge-green" :
                                    "vencido".equals(p.getEstado()) ? "badge-red" :
                                    "cerrado".equals(p.getEstado()) ? "badge-gray" : "badge-yellow" %>">
                                    <%= p.getEstado() %>
                                </span>
                            </td>
                            <td><%= p.getFechaInicio() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/prestamo/eliminar?id=<%= p.getIdPrestamo() %>"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('¿Eliminar?')">Eliminar</a>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="tab-pagos" class="tab-content">
        <div class="card">
            <h3>💳 Historial de Pagos</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID</th><th>ID Préstamo</th><th>Monto</th><th>Fecha</th><th>Estado</th></tr></thead>
                    <tbody>
                    <% if (pagos != null) for (Pago p : pagos) { %>
                        <tr>
                            <td><%= p.getIdPago() %></td>
                            <td><%= p.getIdPrestamo() %></td>
                            <td>$<%= String.format("%,.0f", p.getMonto()) %></td>
                            <td><%= p.getFechaPago() %></td>
                            <td><span class="badge badge-green"><%= p.getEstado() %></span></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
function mostrarTab(nombre) {
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.getElementById('tab-' + nombre).classList.add('active');
    event.target.classList.add('active');
}
</script>
</body>
</html>
