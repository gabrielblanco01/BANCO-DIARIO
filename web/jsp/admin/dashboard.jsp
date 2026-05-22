<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bancodiario.modelo.Usuario, java.util.List" %>
<%
    Usuario sesion = (Usuario) session.getAttribute("usuario");
    if (sesion == null || sesion.getIdRol() != 1) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    String msg = request.getParameter("msg");
    String[] roles = {"", "Administrador", "Prestamista", "Prestatario", "AnalistaCredito", "AgenteCobranza", "SoporteTecnico"};
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Banco Diario - Administrador</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="brand">💰 Banco Diario</span>
    <div class="user-info">
        <span>👤 <%= sesion.getNombre() %> | Administrador</span>
        <form method="get" action="<%= request.getContextPath() %>/logout" style="display:inline">
            <button type="submit" class="btn-logout">Cerrar sesión</button>
        </form>
    </div>
</nav>

<div class="container">
    <div class="page-title">Panel de Administrador</div>

    <% if ("creado".equals(msg)) { %><div class="alert alert-success">✅ Usuario creado correctamente.</div><% } %>
    <% if ("editado".equals(msg)) { %><div class="alert alert-success">✅ Usuario actualizado.</div><% } %>
    <% if ("eliminado".equals(msg)) { %><div class="alert alert-info">🗑️ Usuario eliminado.</div><% } %>
    <% if ("error".equals(msg)) { %><div class="alert alert-error">❌ Error al procesar la solicitud.</div><% } %>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-num"><%= usuarios != null ? usuarios.size() : 0 %></div>
            <div class="stat-label">Usuarios totales</div>
        </div>
    </div>

    <!-- Formulario crear usuario -->
    <div class="card">
        <h3>➕ Crear nuevo usuario</h3>
        <form method="post" action="<%= request.getContextPath() %>/admin/crear">
            <div class="form-row">
                <div class="form-group"><label>Nombre</label><input type="text" name="nombre" required></div>
                <div class="form-group"><label>Email</label><input type="email" name="email" required></div>
                <div class="form-group"><label>Contraseña</label><input type="password" name="contrasena" required></div>
                <div class="form-group"><label>Teléfono</label><input type="text" name="telefono"></div>
                <div class="form-group">
                    <label>Rol</label>
                    <select name="idRol">
                        <option value="1">Administrador</option>
                        <option value="2">Prestamista</option>
                        <option value="3" selected>Prestatario</option>
                        <option value="4">Analista de Crédito</option>
                        <option value="5">Agente de Cobranza</option>
                        <option value="6">Soporte Técnico</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-success">Crear usuario</button>
        </form>
    </div>

    <!-- Tabla de usuarios -->
    <div class="card">
        <h3>👥 Gestión de Usuarios</h3>
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr><th>ID</th><th>Nombre</th><th>Email</th><th>Teléfono</th><th>Rol</th><th>Estado</th><th>Acciones</th></tr>
                </thead>
                <tbody>
                <% if (usuarios != null) for (Usuario u : usuarios) { %>
                    <tr>
                        <td><%= u.getIdUsuario() %></td>
                        <td><%= u.getNombre() %></td>
                        <td><%= u.getEmail() %></td>
                        <td><%= u.getTelefono() != null ? u.getTelefono() : "-" %></td>
                        <td><%= (u.getIdRol() >= 1 && u.getIdRol() <= 6) ? roles[u.getIdRol()] : "?" %></td>
                        <td>
                            <span class="badge <%= "activo".equals(u.getEstado()) ? "badge-green" : "badge-red" %>">
                                <%= u.getEstado() %>
                            </span>
                        </td>
                        <td>
                            <a href="<%= request.getContextPath() %>/admin/eliminar?id=<%= u.getIdUsuario() %>"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Eliminar usuario?')">Eliminar</a>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
