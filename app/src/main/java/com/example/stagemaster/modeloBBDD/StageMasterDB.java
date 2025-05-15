package com.example.stagemaster.modeloBBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.stagemaster.vistas.Hash;

public class StageMasterDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "stageMasterDB";
    public static final Integer DB_VERSION = 1;
    public static final String TABLA_USUARIOS = "usuario";
    public static final String SENTENCIA_CREACION_TABLA_USUARIOS = "create table usuario " +
            "(idUsuario integer not null primary key autoincrement, nombre text not null, apellidos text not null, nombreUsuario text not null," +
            "email text not null, clave text not null)";

    public static final String SENTENCIA_SELECCION_USUARIOS = "select * from usuario where email = ?";
    public static final String SENTENCIA_SELECCION_USUARIOS_NOMBRES = "select * from usuario where nombreUsuario = ?";

    public StageMasterDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS usuario");

        db.execSQL(SENTENCIA_CREACION_TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

    public int insertarUsuario (SQLiteDatabase db, Usuario nuevoUsuario) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("nombre", nuevoUsuario.getNombre());
        valores.put("apellidos", nuevoUsuario.getApellidos());
        valores.put("nombreUsuario", nuevoUsuario.getNombreUsuario());
        valores.put("email", nuevoUsuario.getEmail());
        valores.put("clave", Hash.md5(nuevoUsuario.getClave()));
        resultado = (int) db.insert(TABLA_USUARIOS, null, valores);
        return resultado;
    }

    public Usuario selectUsuarios(SQLiteDatabase db, String email) {
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_USUARIOS, new String[]{email});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreSelect = cursor.getString(cursor.getColumnIndex("nombre"));
            @SuppressLint("Range") String apellidosSelect = cursor.getString(cursor.getColumnIndex("apellidos"));
            @SuppressLint("Range") String nombreUsuarioSelect = cursor.getString(cursor.getColumnIndex("nombreUsuario"));
            @SuppressLint("Range") String emailSelect = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String claveSelect = cursor.getString(cursor.getColumnIndex("clave"));
            return new Usuario(nombreSelect, apellidosSelect, nombreUsuarioSelect, emailSelect, claveSelect);
        }
        cursor.close();
        return null;
    }

    public Usuario selectUsuariosNombreUsuario(SQLiteDatabase db, String nombreUsuario) {
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_USUARIOS_NOMBRES, new String[]{nombreUsuario});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreSelect = cursor.getString(cursor.getColumnIndex("nombre"));
            @SuppressLint("Range") String apellidosSelect = cursor.getString(cursor.getColumnIndex("apellidos"));
            @SuppressLint("Range") String nombreUsuarioSelect = cursor.getString(cursor.getColumnIndex("nombreUsuario"));
            @SuppressLint("Range") String emailSelect = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String claveSelect = cursor.getString(cursor.getColumnIndex("clave"));
            return new Usuario(nombreSelect, apellidosSelect, nombreUsuarioSelect, emailSelect, claveSelect);
        }
        cursor.close();
        return null;
    }

    public int actualizarUsuarioClave(SQLiteDatabase db, String email, String nuevaClave) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("clave", Hash.md5(nuevaClave));
        resultado = db.update(TABLA_USUARIOS, valores, "email = ?", new String[]{email});
        return resultado;
    }

    public int actualizarNombreUsuario(SQLiteDatabase db, String usuario, String nuevoNonbreUsuario) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("nombreUsuario", nuevoNonbreUsuario);
        resultado = db.update(TABLA_USUARIOS, valores, "nombreUsuario = ?", new String[]{usuario});
        return resultado;
    }
}
