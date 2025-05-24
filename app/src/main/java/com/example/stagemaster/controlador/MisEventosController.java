package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stagemaster.modeloBBDD.Evento;
import com.example.stagemaster.modeloBBDD.MisEventos;
import com.example.stagemaster.modeloBBDD.StageMasterDB;
import com.example.stagemaster.modeloBBDD.Usuario;

public class MisEventosController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    public MisEventosController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarEventoUsuario(Evento idEvento, Usuario idUsuario) {
        MisEventos nuevoEventoUsuario = new MisEventos(idEvento.getIdEvento(), idUsuario.getIdUsuario());
        return conexion.insertarEventoUsuario(db, nuevoEventoUsuario);
    }
}
