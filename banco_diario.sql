-- ============================================================
--  Banco Diario - Script de Base de Datos
--  Ejecutar en Railway PostgreSQL al crear el servicio
-- ============================================================

CREATE TABLE IF NOT EXISTS rol (
    id_rol   SERIAL PRIMARY KEY,
    nombre   VARCHAR(50) NOT NULL
);

INSERT INTO rol (nombre) VALUES
    ('Administrador'),
    ('Prestamista'),
    ('Prestatario'),
    ('AnalistaCredito'),
    ('AgenteCobranza'),
    ('SoporteTecnico')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario      SERIAL PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    contrasena      VARCHAR(255) NOT NULL,
    telefono        VARCHAR(20),
    estado          VARCHAR(20) DEFAULT 'activo',
    fecha_registro  TIMESTAMP DEFAULT NOW(),
    id_rol          INT REFERENCES rol(id_rol)
);

CREATE TABLE IF NOT EXISTS configuracion (
    id_configuracion    SERIAL PRIMARY KEY,
    tasa_interes        DECIMAL(5,2),
    plazo_maximo        INT,
    monto_minimo        DECIMAL(12,2),
    monto_maximo        DECIMAL(12,2),
    fecha_actualizacion TIMESTAMP DEFAULT NOW()
);

INSERT INTO configuracion (tasa_interes, plazo_maximo, monto_minimo, monto_maximo)
VALUES (2.5, 24, 100000, 2000000)
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS solicitud (
    id_solicitud    SERIAL PRIMARY KEY,
    id_usuario      INT REFERENCES usuario(id_usuario),
    monto           DECIMAL(12,2) NOT NULL,
    plazo           INT NOT NULL,
    interes         DECIMAL(5,2),
    estado          VARCHAR(20) DEFAULT 'pendiente',
    fecha_solicitud TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS prestamo (
    id_prestamo  SERIAL PRIMARY KEY,
    id_solicitud INT REFERENCES solicitud(id_solicitud),
    monto        DECIMAL(12,2) NOT NULL,
    interes      DECIMAL(5,2),
    plazo        INT,
    estado       VARCHAR(20) DEFAULT 'activo',
    fecha_inicio TIMESTAMP DEFAULT NOW(),
    -- montoDB: total a pagar (capital + intereses) calculado al aprobar
    monto_db     DECIMAL(12,2)
);

CREATE TABLE IF NOT EXISTS pago (
    id_pago     SERIAL PRIMARY KEY,
    id_prestamo INT REFERENCES prestamo(id_prestamo),
    monto       DECIMAL(12,2) NOT NULL,
    fecha_pago  TIMESTAMP DEFAULT NOW(),
    estado      VARCHAR(20) DEFAULT 'pendiente'
);

CREATE TABLE IF NOT EXISTS cobranza (
    id_cobranza  SERIAL PRIMARY KEY,
    id_prestamo  INT REFERENCES prestamo(id_prestamo),
    id_usuario   INT REFERENCES usuario(id_usuario),
    estado       VARCHAR(20) DEFAULT 'abierto',
    fecha        TIMESTAMP DEFAULT NOW(),
    comentario   TEXT
);

CREATE TABLE IF NOT EXISTS incidencia (
    id_incidencia SERIAL PRIMARY KEY,
    id_usuario    INT REFERENCES usuario(id_usuario),
    descripcion   TEXT,
    estado        VARCHAR(20) DEFAULT 'abierto',
    fecha         TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS evaluacion (
    id_evaluacion SERIAL PRIMARY KEY,
    id_solicitud  INT REFERENCES solicitud(id_solicitud),
    resultado     VARCHAR(20),
    observaciones TEXT,
    fecha         TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS notificacion (
    id_notificacion SERIAL PRIMARY KEY,
    id_usuario      INT REFERENCES usuario(id_usuario),
    mensaje         TEXT,
    fecha           TIMESTAMP DEFAULT NOW(),
    leido           BOOLEAN DEFAULT FALSE
);

-- Usuario admin por defecto (contraseña: admin123)
INSERT INTO usuario (nombre, email, contrasena, telefono, estado, id_rol)
VALUES ('Admin Sistema', 'admin@bancodiario.com', 'admin123', '3000000000', 'activo', 1)
ON CONFLICT (email) DO NOTHING;
