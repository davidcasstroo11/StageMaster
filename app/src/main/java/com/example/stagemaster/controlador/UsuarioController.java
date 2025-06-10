package com.example.stagemaster.controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.stagemaster.modeloBBDD.StageMasterDB;
import com.example.stagemaster.modeloBBDD.Usuario;

public class UsuarioController {
    public SQLiteDatabase db;
    public StageMasterDB conexion;

    /**
     * Constructor del controlador de la tabla usuario. Además, establece al incializarse la conexión con la BBDD.
     * @param contexto Contexto actual
     */
    public UsuarioController(Context contexto) {
        conexion = new StageMasterDB(contexto);
        db = conexion.getWritableDatabase();
    }

    public int insertarUsuario(String nombre, String apellidos, String nombreUsuario, String email, String clave) {
        Usuario nuevoUsuario = new Usuario(nombre, apellidos, nombreUsuario, email, clave);
        return conexion.insertarUsuario(db, nuevoUsuario);
    }

    public Usuario selectUsuarios(String email) {
        return conexion.selectUsuarios(db, email);
    }

    public Usuario selectUsuariosNombreUsuarios(String nombreUsuario) {
        return conexion.selectUsuariosNombreUsuario(db, nombreUsuario);
    }

    public int actualizarUsuarioClave(String email, String clave) {
        return conexion.actualizarUsuarioClave(db, email, clave);
    }

    public int actualizarNombreUsuario(String usuario, String nuevoUsuario) {
        return conexion.actualizarNombreUsuario(db, usuario, nuevoUsuario);
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
