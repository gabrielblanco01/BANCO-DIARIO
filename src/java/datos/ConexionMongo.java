package datos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

    // Railway inyecta MONGO_URL como variable de entorno
    private static final String URI      = System.getenv("MONGO_URL") != null
            ? System.getenv("MONGO_URL") : "mongodb://localhost:27017";
    private static final String DATABASE = "bancodiario_logs";
    private static MongoClient cliente   = null;

    private ConexionMongo() {}

    public static MongoDatabase getDatabase() {
        if (cliente == null) {
            cliente = MongoClients.create(URI);
            System.out.println("Conexion establecida con MongoDB.");
        }
        return cliente.getDatabase(DATABASE);
    }

    public static void cerrarConexion() {
        if (cliente != null) {
            cliente.close();
        }
    }
}
