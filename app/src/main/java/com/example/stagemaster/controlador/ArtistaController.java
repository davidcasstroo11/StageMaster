package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stagemaster.modeloBBDD.Artista;
import com.example.stagemaster.modeloBBDD.StageMasterDB;

import java.util.Date;

public class ArtistaController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    public ArtistaController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarArtista(String nombreArtistico, String nacionalidad, String fechaNacimiento, Integer foto) {
        Artista artista = new Artista(nombreArtistico, nacionalidad, fechaNacimiento, foto);
        return conexion.insertarArtista(db, artista);
    }

    public Artista obtenerIdArtistaPorNombre(String nombreArtista) {
        return conexion.obtenerIdArtistaPorNombre(db, nombreArtista);
    }
}
