<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Banco Diario - Iniciar Sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-wrapper">
    <div class="login-box">
        <h2>💰 BANCO DIARIO</h2>
        <p>Plataforma Digital de Préstamos</p>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label>Correo electrónico</label>
                <input type="email" name="email" placeholder="usuario@bancodiario.com" required autofocus>
            </div>
            <div class="form-group">
                <label>Contraseña</label>
                <input type="password" name="contrasena" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%; margin-top:8px;">Ingresar</button>
        </form>

        <p style="text-align:center; margin-top:20px; font-size:0.8rem; color:#aaa;">
            Universidad Autónoma del Caribe · 2025
        </p>
    </div>
</div>
</body>
</html>
