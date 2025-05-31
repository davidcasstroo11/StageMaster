package com.example.stagemaster.modeloBBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.stagemaster.vistas.Hash;

import java.util.ArrayList;
import java.util.Random;

public class StageMasterDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "stageMasterDB";
    public static final Integer DB_VERSION = 9;
    public static final String TABLA_USUARIOS = "usuario";
    public static final String TABLA_ARTISTA = "artista";
    public static final String TABLA_EVENTOS = "evento";
    public static final String TABLA_MISEVENTOS = "misEventos";

    public static final String SENTENCIA_CREACION_TABLA_USUARIOS = "create table usuario " +
            "(idUsuario integer not null primary key autoincrement, nombre text not null, apellidos text not null, nombreUsuario text not null," +
            "email text not null, clave text not null)";
    public static final String SENTENCIA_CREACION_TABLA_ARTISTA = "create table artista " +
            "(idArtista integer not null primary key autoincrement, nombreArtistico text not null, nacionalidad text not null, fechaNacimiento date not null," +
            "foto integer not null)";
    public static final String SENTENCIA_CREACION_TABLA_EVENTOS = "create table evento " +
            "(idEvento integer not null primary key autoincrement, nombreArtista text not null, precio double not null, sede text not null," +
            "pais text not null, entradas integer not null, fecha text not null, foto integer not null, idArtista integer not null, foreign key (idArtista) references artista(idArtista))";
    public static final String SENTENCIA_CREACION_TABLA_MISEVENTOS = "create table misEventos " +
            "(idMisEventos integer not null primary key autoincrement, idEvento integer not null, idUsuario integer not null, codReferencia text," +
            "foreign key (idEvento) references evento(idEvento), foreign key (idUsuario) references usuario(idUsuario))";

    public static final String SENTENCIA_SELECCION_USUARIOS = "select * from usuario where email = ?";
    public static final String SENTENCIA_SELECCION_USUARIOS_NOMBRES = "select * from usuario where nombreUsuario = ?";
    public static final String SENTENCIA_SELECCION_EVENTOS_ENTRADAS = "select * from evento order by entradas desc limit 3";
    public static final String SENTENCIA_SELECCION_OTROS_EVENTOS = "select * from evento order by fecha desc limit 3";
    public static final String SENTENCIA_SELECCION_EVENTOS_NOMBRES = "select * from evento where nombreArtista = ?";
    public static final String SENTENCIA_SELECCION_EVENTOS_ID = "select * from evento where idEvento = ?";
    public static final String SENTENCIA_SELECCION_MISEVENTOS_USUARIO = "select * from misEventos where idUsuario = ?";

    public StageMasterDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS artista");
        db.execSQL("DROP TABLE IF EXISTS evento");
        db.execSQL("DROP TABLE IF EXISTS misEventos");

        db.execSQL(SENTENCIA_CREACION_TABLA_USUARIOS);
        db.execSQL(SENTENCIA_CREACION_TABLA_ARTISTA);
        db.execSQL(SENTENCIA_CREACION_TABLA_EVENTOS);
        db.execSQL(SENTENCIA_CREACION_TABLA_MISEVENTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS artista");
        db.execSQL("DROP TABLE IF EXISTS evento");
        db.execSQL("DROP TABLE IF EXISTS misEventos");
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
            @SuppressLint("Range") int idUsuario = cursor.getInt(cursor.getColumnIndex("idUsuario"));
            @SuppressLint("Range") String nombreSelect = cursor.getString(cursor.getColumnIndex("nombre"));
            @SuppressLint("Range") String apellidosSelect = cursor.getString(cursor.getColumnIndex("apellidos"));
            @SuppressLint("Range") String nombreUsuarioSelect = cursor.getString(cursor.getColumnIndex("nombreUsuario"));
            @SuppressLint("Range") String emailSelect = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String claveSelect = cursor.getString(cursor.getColumnIndex("clave"));
            return new Usuario(idUsuario, nombreSelect, apellidosSelect, nombreUsuarioSelect, emailSelect, claveSelect);
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

    public int insertarArtista(SQLiteDatabase db, Artista nuevoArtista) {
        ContentValues valores = new ContentValues();
        valores.put("nombreArtista", nuevoArtista.getNombreArtista());
        valores.put("nacionalidad", nuevoArtista.getNacionalidad());
        valores.put("fechaNacimiento", nuevoArtista.getFechaNacimiento().getTime());
        valores.put("foto", nuevoArtista.getFoto());
        db.insert(TABLA_ARTISTA, null, valores);
        return nuevoArtista.getIdArtista();
    }

    public int insertarEvento(SQLiteDatabase db, Evento nuevoEvento) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("nombreArtista", nuevoEvento.getNombreArtista());
        valores.put("precio", nuevoEvento.getPrecio());
        valores.put("sede", nuevoEvento.getSede());
        valores.put("pais", nuevoEvento.getPais());
        valores.put("entradas", nuevoEvento.getEntradas());
        valores.put("fecha", nuevoEvento.getFecha());
        valores.put("foto", nuevoEvento.getFoto());
        valores.put("idArtista", nuevoEvento.getIdArtista());
        resultado = (int) db.insert(TABLA_EVENTOS, null, valores);
        return resultado;
    }

    public ArrayList<Evento> selectEventosEntradas(SQLiteDatabase db) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_EVENTOS_ENTRADAS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") int entradas = cursor.getInt(cursor.getColumnIndex("entradas"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(nombreArtista, precio, sede, pais, entradas, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public ArrayList<Evento> selectOtrosEventos(SQLiteDatabase db) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_OTROS_EVENTOS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") int entradas = cursor.getInt(cursor.getColumnIndex("entradas"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(nombreArtista, precio, sede, pais, entradas, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public ArrayList<Evento> selectEventosNombres(SQLiteDatabase db, String nombreArtistaConsulta) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_EVENTOS_NOMBRES, new String[]{nombreArtistaConsulta});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idEvento = cursor.getInt(cursor.getColumnIndex("idEvento"));
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") int entradas = cursor.getInt(cursor.getColumnIndex("entradas"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(idEvento,nombreArtista, precio, sede, pais, entradas, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public ArrayList<Evento> selectEventosId(SQLiteDatabase db, int idEvento) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_EVENTOS_ID, new String[]{String.valueOf(idEvento)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idEventoS = cursor.getInt(cursor.getColumnIndex("idEvento"));
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") int entradas = cursor.getInt(cursor.getColumnIndex("entradas"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(idEventoS,nombreArtista, precio, sede, pais, entradas, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public int insertarEventoUsuario(SQLiteDatabase db, MisEventos eventos) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("idEvento", eventos.getIdEvento());
        valores.put("idUsuario", eventos.getIdUsuario());
        resultado = (int) db.insert(TABLA_MISEVENTOS, null, valores);
        return resultado;
    }

    public int actualizarCodReferenciaEvento(SQLiteDatabase db, Evento evento, Usuario usuario) {
        int resultado;
        ContentValues valores = new ContentValues();
        Random random = new Random();
        String codReferencia = String.valueOf(10000000 + random.nextInt(90000000));
        valores.put("codReferencia", codReferencia);
        resultado = db.update(TABLA_MISEVENTOS, valores, "idEvento = ? and idUsuario = ?", new String[]{String.valueOf(evento.getIdEvento()), String.valueOf(usuario.getIdUsuario())});
        return resultado;
    }

    public ArrayList<MisEventos> selectMisEventos(SQLiteDatabase db, int idUsuarioWhere) {
        ArrayList<MisEventos> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_MISEVENTOS_USUARIO, new String[]{String.valueOf(idUsuarioWhere)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idEvento = cursor.getInt(cursor.getColumnIndex("idEvento"));
                @SuppressLint("Range") int idUsuario = cursor.getInt(cursor.getColumnIndex("idUsuario"));
                @SuppressLint("Range") String codReferencia = cursor.getString(cursor.getColumnIndex("idUsuario"));
                eventos.add(new MisEventos(idEvento,idUsuario,codReferencia));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public ArrayList<Evento> selectEventosOrdenacion(SQLiteDatabase db, String metodoOrdenacion) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from evento order by " + metodoOrdenacion, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") int entradas = cursor.getInt(cursor.getColumnIndex("entradas"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(nombreArtista, precio, sede, pais, entradas, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    public int eliminarEventoUsuario(SQLiteDatabase db, MisEventos misEventos) {
        int resultado;
        resultado = db.delete(TABLA_MISEVENTOS, "idUsuario = ? and idEvento = ?", new String[]{String.valueOf(misEventos.getIdUsuario()),String.valueOf(misEventos.getIdEvento())});
        return resultado;
    }

    public MisEventos selectMisEventosCod(SQLiteDatabase db, int idEventoWhere, int idUsuarioWhere) {
        Cursor cursor = db.rawQuery("select * from misEventos where idEvento = ? and idUsuario = ?", new String[]{String.valueOf(idEventoWhere),String.valueOf(idUsuarioWhere)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int idEvento = cursor.getInt(cursor.getColumnIndex("idEvento"));
            @SuppressLint("Range") int idUsuario = cursor.getInt(cursor.getColumnIndex("idUsuario"));
            @SuppressLint("Range") String codReferencia = cursor.getString(cursor.getColumnIndex("idUsuario"));
            return new MisEventos(idEvento,idUsuario,codReferencia);
        }
        cursor.close();
        return null;
    }

}
