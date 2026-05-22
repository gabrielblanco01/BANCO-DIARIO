<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bancodiario.modelo.*, java.util.List" %>
<%
    Usuario sesion = (Usuario) session.getAttribute("usuario");
    if (sesion == null || sesion.getIdRol() != 4) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Solicitud> solicitudes = (List<Solicitud>) request.getAttribute("solicitudes");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Banco Diario - Analista de Crédito</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="brand">💰 Banco Diario</span>
    <div class="user-info">
        <span>👤 <%= sesion.getNombre() %> | Analista de Crédito</span>
        <form method="get" action="<%= request.getContextPath() %>/logout" style="display:inline">
            <button type="submit" class="btn-logout">Cerrar sesión</button>
        </form>
    </div>
</nav>

<div class="container">
    <div class="page-title">Evaluación de Solicitudes de Crédito</div>

    <% if ("aprobado".equals(msg)) { %><div class="alert alert-success">✅ Solicitud aprobada. Préstamo creado.</div><% } %>
    <% if ("rechazado".equals(msg)) { %><div class="alert alert-error">❌ Solicitud rechazada.</div><% } %>

    <div class="card">
        <h3>📋 Solicitudes Pendientes</h3>
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr><th>ID</th><th>ID Usuario</th><th>Monto</th><th>Plazo</th><th>Interés</th><th>Estado</th><th>Fecha</th><th>Acciones</th></tr>
                </thead>
                <tbody>
                <% if (solicitudes != null && !solicitudes.isEmpty()) {
                   for (Solicitud s : solicitudes) { %>
                    <tr>
                        <td><%= s.getIdSolicitud() %></td>
                        <td><%= s.getIdUsuario() %></td>
                        <td>$<%= String.format("%,.0f", s.getMonto()) %></td>
                        <td><%= s.getPlazo() %> meses</td>
                        <td><%= s.getInteres() %>%</td>
                        <td><span class="badge badge-yellow"><%= s.getEstado() %></span></td>
                        <td><%= s.getFechaSolicitud() %></td>
                        <td style="display:flex; gap:6px;">
                            <form method="post" action="<%= request.getContextPath() %>/analista/aprobar" style="display:inline">
                                <input type="hidden" name="idSolicitud" value="<%= s.getIdSolicitud() %>">
                                <button type="submit" class="btn btn-success btn-sm"
                                        onclick="return confirm('¿Aprobar solicitud <%= s.getIdSolicitud() %>?')">✓ Aprobar</button>
                            </form>
                            <form method="post" action="<%= request.getContextPath() %>/analista/rechazar" style="display:inline">
                                <input type="hidden" name="idSolicitud" value="<%= s.getIdSolicitud() %>">
                                <button type="submit" class="btn btn-danger btn-sm"
                                        onclick="return confirm('¿Rechazar solicitud <%= s.getIdSolicitud() %>?')">✗ Rechazar</button>
                            </form>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="8" style="text-align:center; padding:24px; color:#888;">No hay solicitudes pendientes.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
