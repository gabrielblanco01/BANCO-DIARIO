package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Railway inyecta estas variables de entorno automáticamente
    private static final String URL      = System.getenv("DATABASE_URL") != null
            ? System.getenv("DATABASE_URL")
            : "jdbc:postgresql://127.0.0.1:5432/banco_diario";
    private static final String USUARIO  = System.getenv("PGUSER")     != null
            ? System.getenv("PGUSER")     : "postgres";
    private static final String PASSWORD = System.getenv("PGPASSWORD") != null
            ? System.getenv("PGPASSWORD") : "1234";

    private static Connection instancia = null;

    private Conexion() {}

    public static Connection getConexion() {
        try {
            if (instancia == null || instancia.isClosed()) {
                Class.forName("org.postgresql.Driver");
                instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida con PostgreSQL.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return instancia;
    }

    public static void cerrarConexion() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }
}
