package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stagemaster.modeloBBDD.Evento;
import com.example.stagemaster.modeloBBDD.StageMasterDB;

import java.util.ArrayList;

public class EventoController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    public EventoController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarEvento (String nombreArtistico, double precio, String sede, String pais, Integer entradas, String fecha, Integer foto, Integer idArtista ) {
        Evento evento = new Evento(nombreArtistico, precio, sede, pais, entradas, fecha, foto, idArtista);
        return conexion.insertarEvento(db, evento);
    }

    public ArrayList<Evento> selectEventosEntradas() {
        return conexion.selectEventosEntradas(db);
    }

    public ArrayList<Evento> selectOtrosEventos() {
        return conexion.selectOtrosEventos(db);
    }

    public ArrayList<Evento> selectEventosNombres(String nombreArtista) {
        return conexion.selectEventosNombres(db, nombreArtista);
    }

}

