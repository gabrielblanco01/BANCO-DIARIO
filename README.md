# 🚀 Banco Diario - Deploy en Railway

## Pasos para publicar en internet

---

### 1. Crear cuenta en Railway
- Ve a https://railway.app
- Regístrate con tu cuenta de GitHub (necesitas GitHub)

---

### 2. Subir el proyecto a GitHub
1. Crea un repositorio nuevo en https://github.com (llámalo `banco-diario`)
2. Sube TODA esta carpeta al repositorio

---

### 3. Crear el proyecto en Railway

1. En Railway: **New Project → Deploy from GitHub repo**
2. Selecciona tu repositorio `banco-diario`
3. Railway detecta el `Dockerfile` automáticamente ✅

---

### 4. Agregar PostgreSQL

1. En tu proyecto Railway: **+ New → Database → PostgreSQL**
2. Railway crea la BD y genera las variables automáticamente:
   - `DATABASE_URL` ✅
   - `PGUSER` ✅  
   - `PGPASSWORD` ✅

---

### 5. Cargar el script SQL

1. En el servicio PostgreSQL → pestaña **Query**
2. Pega el contenido del archivo `banco_diario.sql`
3. Ejecuta → crea todas las tablas ✅

---

### 6. Agregar MongoDB (para los logs)

1. En Railway: **+ New → Database → MongoDB**
2. Copia la variable `MONGO_URL` que genera
3. Ve a tu servicio Java → **Variables** → agrega `MONGO_URL` con ese valor

---

### 7. Generar dominio público

1. En tu servicio Java → pestaña **Settings**
2. **Networking → Generate Domain**
3. Railway te da una URL tipo:
   ```
   https://banco-diario-production.up.railway.app
   ```

---

### ✅ Listo — accede desde cualquier navegador

```
https://banco-diario-production.up.railway.app/login
```

**Usuario admin por defecto:**
- Email: `admin@bancodiario.com`
- Contraseña: `admin123`

---

### ⚠️ Importante antes de subir

En NetBeans: **Clean and Build** (Shift+F11) para regenerar el WAR
El archivo `dist/BancoDiarioFacade.war` debe estar actualizado
