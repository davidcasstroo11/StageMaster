package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stagemaster.modeloBBDD.Evento;
import com.example.stagemaster.modeloBBDD.MisEventos;
import com.example.stagemaster.modeloBBDD.StageMasterDB;
import com.example.stagemaster.modeloBBDD.Usuario;

import java.util.ArrayList;
import java.util.Random;

public class MisEventosController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    public MisEventosController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarEventoUsuario(Evento idEvento, Usuario idUsuario) {
        MisEventos nuevoEventoUsuario = new MisEventos(idEvento.getIdEvento(), idUsuario.getIdUsuario(), "");
        return conexion.insertarEventoUsuario(db, nuevoEventoUsuario);
    }

    public ArrayList<MisEventos> selectEventosUsuario(Usuario usuario) {
        return conexion.selectMisEventos(db, usuario.getIdUsuario());
    }

    public int actualizarCodReferenciaEventos(Evento evento, Usuario usuario){
        return conexion.actualizarCodReferenciaEvento(db, evento, usuario);
    }

    public MisEventos selectMisEventosCod(Evento evento, Usuario usuario) {
        return conexion.selectMisEventosCod(db, evento.getIdEvento(), usuario.getIdUsuario());
    }

    public int eliminarEventoUsuario(MisEventos eventos) {
        return conexion.eliminarEventoUsuario(db, eventos);
    }
}
