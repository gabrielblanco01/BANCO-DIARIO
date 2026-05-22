<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bancodiario.modelo.*, java.util.List" %>
<%
    Usuario sesion = (Usuario) session.getAttribute("usuario");
    if (sesion == null || sesion.getIdRol() != 5) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Cobranza> cobranzas = (List<Cobranza>) request.getAttribute("cobranzas");
    List<Prestamo> vencidos = (List<Prestamo>) request.getAttribute("vencidos");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Banco Diario - Agente Cobranza</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="brand">💰 Banco Diario</span>
    <div class="user-info">
        <span>👤 <%= sesion.getNombre() %> | Agente de Cobranza</span>
        <form method="get" action="<%= request.getContextPath() %>/logout" style="display:inline">
            <button type="submit" class="btn-logout">Cerrar sesión</button>
        </form>
    </div>
</nav>

<div class="container">
    <div class="page-title">Gestión de Cobranza</div>

    <% if ("registrado".equals(msg)) { %><div class="alert alert-success">✅ Registro de cobranza guardado.</div><% } %>

    <div class="tabs">
        <button class="tab-btn active" onclick="mostrarTab('vencidos')">Préstamos Vencidos</button>
        <button class="tab-btn" onclick="mostrarTab('registrar')">Registrar Contacto</button>
        <button class="tab-btn" onclick="mostrarTab('lista')">Cobranzas Abiertas</button>
    </div>

    <div id="tab-vencidos" class="tab-content active">
        <div class="card">
            <h3>⚠️ Préstamos Vencidos</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID</th><th>Monto</th><th>Interés</th><th>Plazo</th><th>Estado</th><th>Fecha Inicio</th></tr></thead>
                    <tbody>
                    <% if (vencidos != null && !vencidos.isEmpty()) {
                       for (Prestamo p : vencidos) { %>
                        <tr>
                            <td><%= p.getIdPrestamo() %></td>
                            <td>$<%= String.format("%,.0f", p.getMonto()) %></td>
                            <td><%= p.getInteres() %>%</td>
                            <td><%= p.getPlazo() %> meses</td>
                            <td><span class="badge badge-red"><%= p.getEstado() %></span></td>
                            <td><%= p.getFechaInicio() %></td>
                        </tr>
                    <% } } else { %>
                        <tr><td colspan="6" style="text-align:center; padding:20px; color:#888;">Sin préstamos vencidos.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="tab-registrar" class="tab-content">
        <div class="card">
            <h3>📞 Registrar contacto con deudor</h3>
            <form method="post" action="<%= request.getContextPath() %>/cobranza/lista">
                <div class="form-group"><label>ID Préstamo</label><input type="number" name="idPrestamo" required></div>
                <div class="form-group"><label>Comentario / Observaciones</label><textarea name="comentario" rows="4" required></textarea></div>
                <button type="submit" class="btn btn-primary">Guardar registro</button>
            </form>
        </div>
    </div>

    <div id="tab-lista" class="tab-content">
        <div class="card">
            <h3>📋 Cobranzas Abiertas</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID</th><th>ID Préstamo</th><th>Estado</th><th>Fecha</th><th>Comentario</th></tr></thead>
                    <tbody>
                    <% if (cobranzas != null) for (Cobranza c : cobranzas) { %>
                        <tr>
                            <td><%= c.getIdCobranza() %></td>
                            <td><%= c.getIdPrestamo() %></td>
                            <td><span class="badge badge-yellow"><%= c.getEstado() %></span></td>
                            <td><%= c.getFecha() %></td>
                            <td><%= c.getComentario() %></td>
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
