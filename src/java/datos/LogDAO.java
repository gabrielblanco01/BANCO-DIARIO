package bancodiario.datos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Date;

public class LogDAO {

    private static MongoCollection<Document> getColeccion(String coleccion) {
        MongoDatabase db = ConexionMongo.getDatabase();
        return db.getCollection(coleccion);
    }

    // Log de actividad general (login, logout, acciones)
    public static void registrarActividad(String accion, String usuario, String resultado) {
        Document log = new Document()
            .append("accion", accion)
            .append("usuario", usuario)
            .append("resultado", resultado)
            .append("fecha", new Date());
        getColeccion("logs_actividad").insertOne(log);
    }

    // Log de prestamos (aprobar, rechazar, crear)
    public static void registrarPrestamo(String accion, int idSolicitud, String realizadoPor) {
        Document log = new Document()
            .append("accion", accion)
            .append("idSolicitud", idSolicitud)
            .append("realizadoPor", realizadoPor)
            .append("fecha", new Date());
        getColeccion("logs_prestamos").insertOne(log);
    }

    // Log de pagos
    public static void registrarPago(int idPrestamo, double monto, String estado) {
        Document log = new Document()
            .append("idPrestamo", idPrestamo)
            .append("monto", monto)
            .append("estado", estado)
            .append("fecha", new Date());
        getColeccion("logs_pagos").insertOne(log);
    }

    // Log de errores del sistema
    public static void registrarError(String clase, String metodo, String error) {
        Document log = new Document()
            .append("clase", clase)
            .append("metodo", metodo)
            .append("error", error)
            .append("fecha", new Date());
        getColeccion("logs_errores").insertOne(log);
    }
}