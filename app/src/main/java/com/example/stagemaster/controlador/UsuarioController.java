package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stagemaster.modeloBBDD.StageMasterDB;
import com.example.stagemaster.modeloBBDD.Usuario;

public class UsuarioController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    public UsuarioController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarUsuario(String nombre, String apellidos, String nombreUsuario, String clave, String email) {
        Usuario nuevoUsuario = new Usuario(nombre, apellidos, nombreUsuario, email, clave);
        return conexion.insertarUsuario(db, nuevoUsuario);
    }

    public Usuario selectUsuarios(String email) {
        return conexion.selectUsuarios(db, email);
    }
}
