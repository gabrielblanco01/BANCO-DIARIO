<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="bancodiario.modelo.*, java.util.List" %>
<%
    Usuario sesion = (Usuario) session.getAttribute("usuario");
    if (sesion == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Solicitud> solicitudes = (List<Solicitud>) request.getAttribute("solicitudes");
    List<Pago> pagos = (List<Pago>) request.getAttribute("pagos");
    String msg = request.getParameter("msg");
    boolean mostrarSim = "1".equals(request.getParameter("simular"));
    Double simCuota = (Double) session.getAttribute("simCuota");
    Double simTotal = (Double) session.getAttribute("simTotal");
    Double simInteresTotal = (Double) session.getAttribute("simInteresTotal");
    Double simMonto = (Double) session.getAttribute("simMonto");
    Integer simPlazo = (Integer) session.getAttribute("simPlazo");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Banco Diario - Prestatario</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <span class="brand">💰 Banco Diario</span>
    <div class="user-info">
        <span>👤 <%= sesion.getNombre() %> | Prestatario</span>
        <form method="get" action="<%= request.getContextPath() %>/logout" style="display:inline">
            <button type="submit" class="btn-logout">Cerrar sesión</button>
        </form>
    </div>
</nav>

<div class="container">
    <div class="page-title">Mi Panel</div>

    <% if ("solicitado".equals(msg)) { %><div class="alert alert-success">✅ Solicitud enviada. Espera aprobación del analista.</div><% } %>
    <% if ("pagado".equals(msg)) { %><div class="alert alert-success">✅ Pago registrado correctamente.</div><% } %>
    <% if ("error".equals(msg)) { %><div class="alert alert-error">❌ Error. Verifica el monto (100.000 - 2.000.000) y plazo (1-24 meses).</div><% } %>

    <div class="tabs">
        <button class="tab-btn active" onclick="mostrarTab('solicitar')">Solicitar Préstamo</button>
        <button class="tab-btn" onclick="mostrarTab('missolicitudes')">Mis Solicitudes</button>
        <button class="tab-btn" onclick="mostrarTab('pagos')">Mis Pagos</button>
    </div>

    <!-- TAB SOLICITAR -->
    <div id="tab-solicitar" class="tab-content active">
        <div style="display:grid; grid-template-columns:1fr 1fr; gap:20px;">
            <div class="card">
                <h3>📋 Solicitar Préstamo</h3>
                <form method="post" action="<%= request.getContextPath() %>/prestamo/solicitar">
                    <div class="form-group"><label>Monto (COP)</label>
                        <input type="number" name="monto" min="100000" max="2000000" step="1000" placeholder="100.000 - 2.000.000" required>
                    </div>
                    <div class="form-group"><label>Plazo (meses)</label>
                        <input type="number" name="plazo" min="1" max="24" placeholder="1 - 24 meses" required>
                    </div>
                    <div class="form-group"><label>Tasa de interés anual (%)</label>
                        <input type="number" name="interes" min="0" max="100" step="0.1" value="2.5" required>
                    </div>
                    <button type="submit" class="btn btn-success">Enviar solicitud</button>
                </form>
            </div>
            <div class="card">
                <h3>🔢 Simulador de Crédito</h3>
                <form method="post" action="<%= request.getContextPath() %>/prestamo/simular">
                    <div class="form-group"><label>Monto (COP)</label>
                        <input type="number" name="monto" min="100000" max="2000000" step="1000" value="<%= simMonto != null ? simMonto.intValue() : "" %>" required>
                    </div>
                    <div class="form-group"><label>Plazo (meses)</label>
                        <input type="number" name="plazo" min="1" max="24" value="<%= simPlazo != null ? simPlazo : "" %>" required>
                    </div>
                    <div class="form-group"><label>Tasa anual (%)</label>
                        <input type="number" name="interes" min="0" max="100" step="0.1" value="2.5" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Calcular</button>
                </form>
                <% if (simCuota != null) { %>
                <div class="simulador-result">
                    <div class="result-row"><span>Cuota mensual</span><span><b>$<%= String.format("%,.0f", simCuota) %></b></span></div>
                    <div class="result-row"><span>Interés total</span><span>$<%= String.format("%,.0f", simInteresTotal) %></span></div>
                    <div class="result-row"><span>Total a pagar</span><span>$<%= String.format("%,.0f", simTotal) %></span></div>
                </div>
                <% } %>
            </div>
        </div>
    </div>

    <!-- TAB MIS SOLICITUDES -->
    <div id="tab-missolicitudes" class="tab-content">
        <div class="card">
            <h3>📄 Mis Solicitudes</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID</th><th>Monto</th><th>Plazo</th><th>Interés</th><th>Estado</th><th>Fecha</th></tr></thead>
                    <tbody>
                    <% if (solicitudes != null) for (Solicitud s : solicitudes) { %>
                        <tr>
                            <td><%= s.getIdSolicitud() %></td>
                            <td>$<%= String.format("%,.0f", s.getMonto()) %></td>
                            <td><%= s.getPlazo() %> meses</td>
                            <td><%= s.getInteres() %>%</td>
                            <td>
                                <span class="badge <%=
                                    "aprobado".equals(s.getEstado()) ? "badge-green" :
                                    "rechazado".equals(s.getEstado()) ? "badge-red" : "badge-yellow" %>">
                                    <%= s.getEstado() %>
                                </span>
                            </td>
                            <td><%= s.getFechaSolicitud() %></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- TAB PAGOS -->
    <div id="tab-pagos" class="tab-content">
        <div class="card">
            <h3>💳 Registrar Pago</h3>
            <form method="post" action="<%= request.getContextPath() %>/pago/registrar" style="display:flex; gap:12px; align-items:flex-end;">
                <div class="form-group" style="margin:0">
                    <label>ID Préstamo</label>
                    <input type="number" name="idPrestamo" required style="width:120px">
                </div>
                <div class="form-group" style="margin:0">
                    <label>Monto (COP)</label>
                    <input type="number" name="monto" min="1" step="1000" required style="width:160px">
                </div>
                <button type="submit" class="btn btn-primary">Pagar</button>
            </form>
        </div>
        <div class="card">
            <h3>📑 Historial de Pagos</h3>
            <div class="table-wrapper">
                <table>
                    <thead><tr><th>ID Pago</th><th>ID Préstamo</th><th>Monto</th><th>Fecha</th><th>Estado</th></tr></thead>
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
<% if (mostrarSim) { %>
document.querySelectorAll('.tab-btn')[0].click();
<% } %>
</script>
</body>
</html>
