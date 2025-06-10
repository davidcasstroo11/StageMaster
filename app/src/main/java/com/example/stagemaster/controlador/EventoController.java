package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.metrics.Event;

import com.example.stagemaster.modeloBBDD.Evento;
import com.example.stagemaster.modeloBBDD.MisEventos;
import com.example.stagemaster.modeloBBDD.StageMasterDB;
import com.example.stagemaster.modeloBBDD.Usuario;

import java.util.ArrayList;

public class EventoController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    /**
     * Constructor del controlador de la tabla evento. Además, establece al incializarse la conexión con la BBDD.
     * @param contexto Contexto actual
     */
    public EventoController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarEvento (String nombreArtistico, double precio, String sede, String pais, String fecha, Integer foto, Integer idArtista ) {
        Evento evento = new Evento(nombreArtistico, precio, sede, pais, fecha, foto, idArtista);
        return conexion.insertarEvento(db, evento);
    }

    public ArrayList<Evento> selectEventosEntradas() {
        return conexion.selectEventosEntradas(db);
    }

    public ArrayList<Evento> selectOtrosEventos() {
        return conexion.selectOtrosEventos(db);
    }

    public Evento selectEventosNombres(String nombreArtista, String fecha) {
        return conexion.selectEventosNombres(db, nombreArtista, fecha);
    }

    public ArrayList<Evento> selectEventosNombresBuscar(String nombreArtista) {
        return conexion.selectEventosNombresBuscar(db, nombreArtista);
    }

    public ArrayList<Evento> selectEventosId(int idEvento) {
        return conexion.selectEventosId(db, idEvento);
    }

    public ArrayList<Evento> selectEventosOrdenacion(String metodoOrdenacion) {
        return conexion.selectEventosOrdenacion(db, metodoOrdenacion);
    }

    public ArrayList<Evento> selectEventos(){
        return conexion.selectEventos(db);
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public void cerrar() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}

