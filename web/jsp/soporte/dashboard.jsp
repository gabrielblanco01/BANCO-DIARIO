<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bancodiario.modelo.*, java.util.List" %>
<%
    Usuario sesion = (Usuario) session.getAttribute("usuario");
    if (sesion == null || sesion.getIdRol() != 6) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Incidencia> incidencias = (List<Incidencia>) request.getAttribute("incidencias");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Banco Diario - Soporte Técnico</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="brand">💰 Banco Diario</span>
    <div class="user-info">
        <span>👤 <%= sesion.getNombre() %> | Soporte Técnico</span>
        <form method="get" action="<%= request.getContextPath() %>/logout" style="display:inline">
            <button type="submit" class="btn-logout">Cerrar sesión</button>
        </form>
    </div>
</nav>

<div class="container">
    <div class="page-title">Gestión de Incidencias</div>

    <% if ("cerrado".equals(msg)) { %><div class="alert alert-info">✅ Incidencia cerrada.</div><% } %>
    <% if ("reportado".equals(msg)) { %><div class="alert alert-success">✅ Incidencia reportada.</div><% } %>

    <div class="tabs">
        <button class="tab-btn active" onclick="mostrarTab('abiertas')">Incidencias Abiertas</button>
        <button class="tab-btn" onclick="mostrarTab('reportar')">Reportar Nueva</button>
    </div>

    <div id="tab-abiertas" class="tab-content active">
        <div style="margin-bottom:12px;">
            <a href="<%= request.getContextPath() %>/soporte/todas" class="btn btn-primary btn-sm">Ver todas las incidencias</a>
            <a href="<%= request.getContextPath() %>/soporte/incidencias" class="btn btn-warning btn-sm">Solo abiertas</a>
        </div>
        <div class="card">
            <h3>🔧 Incidencias</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID</th><th>ID Usuario</th><th>Descripción</th><th>Estado</th><th>Fecha</th><th>Acción</th></tr></thead>
                    <tbody>
                    <% if (incidencias != null && !incidencias.isEmpty()) {
                       for (Incidencia i : incidencias) { %>
                        <tr>
                            <td><%= i.getIdIncidencia() %></td>
                            <td><%= i.getIdUsuario() %></td>
                            <td><%= i.getDescripcion() %></td>
                            <td>
                                <span class="badge <%= "abierto".equals(i.getEstado()) ? "badge-yellow" : "badge-green" %>">
                                    <%= i.getEstado() %>
                                </span>
                            </td>
                            <td><%= i.getFecha() %></td>
                            <td>
                                <% if ("abierto".equals(i.getEstado())) { %>
                                <a href="<%= request.getContextPath() %>/soporte/cerrar?id=<%= i.getIdIncidencia() %>"
                                   class="btn btn-success btn-sm"
                                   onclick="return confirm('¿Cerrar incidencia?')">Cerrar</a>
                                <% } %>
                            </td>
                        </tr>
                    <% } } else { %>
                        <tr><td colspan="6" style="text-align:center; padding:20px; color:#888;">Sin incidencias.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="tab-reportar" class="tab-content">
        <div class="card">
            <h3>📝 Reportar Nueva Incidencia</h3>
            <form method="post" action="<%= request.getContextPath() %>/soporte/incidencias">
                <div class="form-group"><label>ID Usuario afectado</label><input type="number" name="idUsuario" required></div>
                <div class="form-group"><label>Descripción del problema</label><textarea name="descripcion" rows="4" required></textarea></div>
                <button type="submit" class="btn btn-primary">Reportar</button>
            </form>
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
