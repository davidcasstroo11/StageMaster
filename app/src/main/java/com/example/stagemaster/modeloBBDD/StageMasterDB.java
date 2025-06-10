package com.example.stagemaster.modeloBBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.stagemaster.vistas.Hash;

import java.util.ArrayList;
import java.util.Random;

public class StageMasterDB extends SQLiteOpenHelper {
    /**
     * Constantes definidas para información principal de la BBDD
     */
    public static final String DB_NAME = "stageMasterDB";
    public static final Integer DB_VERSION = 10;
    public static final String TABLA_USUARIOS = "usuario";
    public static final String TABLA_ARTISTA = "artista";
    public static final String TABLA_EVENTOS = "evento";
    public static final String TABLA_MISEVENTOS = "misEventos";

    /**
     * Constantes definidas para la creación de las tablas contenidas en la BBDD
     */
    public static final String SENTENCIA_CREACION_TABLA_USUARIOS = "create table usuario " +
            "(idUsuario integer not null primary key autoincrement, nombre text not null, apellidos text not null, nombreUsuario text not null," +
            "email text not null, clave text not null)";
    public static final String SENTENCIA_CREACION_TABLA_ARTISTA = "create table artista " +
            "(idArtista integer not null primary key autoincrement, nombreArtistico text not null, nacionalidad text not null, fechaNacimiento date not null," +
            "foto integer not null)";
    public static final String SENTENCIA_CREACION_TABLA_EVENTOS = "create table evento " +
            "(idEvento integer not null primary key autoincrement, nombreArtista text not null, precio double not null, sede text not null," +
            "pais text not null, fecha text not null, foto integer not null, idArtista integer not null, foreign key (idArtista) references artista(idArtista))";
    public static final String SENTENCIA_CREACION_TABLA_MISEVENTOS = "create table misEventos " +
            "(idMisEventos integer not null primary key autoincrement, idEvento integer not null, idUsuario integer not null, codReferencia text," +
            "foreign key (idEvento) references evento(idEvento), foreign key (idUsuario) references usuario(idUsuario))";

    /**
     * Constantes definidas para realizar consultas personalizadas
     */
    public static final String SENTENCIA_SELECCION_USUARIOS = "select * from usuario where email = ?";
    public static final String SENTENCIA_SELECCION_USUARIOS_NOMBRES = "select * from usuario where nombreUsuario = ?";
    public static final String SENTENCIA_SELECCION_ARTISTA_NOMBRE = "select * from artista where nombreArtistico = ?";
    public static final String SENTENCIA_SELECCION_EVENTOS_DESTACADOS = "select * from evento order by precio desc limit 3";
    public static final String SENTENCIA_SELECCION_OTROS_EVENTOS = "select * from evento order by fecha asc limit 3";
    public static final String SENTENCIA_SELECCION_EVENTOS_NOMBRES = "select * from evento where nombreArtista = ? and fecha = ?";
    public static final String SENTENCIA_SELECCION_EVENTOS_NOMBRES_BUSCAR = "select * from evento where nombreArtista like ?";
    public static final String SENTENCIA_SELECCION_EVENTOS_ID = "select * from evento where idEvento = ?";
    public static final String SENTENCIA_SELECCION_MISEVENTOS_USUARIO = "select * from misEventos where idUsuario = ?";

    /**
     * Constructor necesario para iniciar conexión con la BBDD
     * @param context Contexto del controlador
     */
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

    /**
     * Método que inserta un nuevo usuario a través de la proporcion de los parámetros del registro
     * @param db BBDD
     * @param nuevoUsuario Usuario a insertar
     * @return Devuelve un entero para comprobar si se han insertado registros en la BBDD
     */
    public int insertarUsuario (SQLiteDatabase db, Usuario nuevoUsuario) {
        try {
            int resultado;
            ContentValues valores = new ContentValues();
            valores.put("nombre", nuevoUsuario.getNombre());
            valores.put("apellidos", nuevoUsuario.getApellidos());
            valores.put("nombreUsuario", nuevoUsuario.getNombreUsuario());
            valores.put("email", nuevoUsuario.getEmail());
            valores.put("clave", Hash.md5(nuevoUsuario.getClave()));
            resultado = (int) db.insert(TABLA_USUARIOS, null, valores);
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Método que realiza una consulta para devolver un usuario con el email configurado en el parámetro
     * @param db BBDD
     * @param email Email del usuario
     * @return Devuelve un objeto Usuario o un valor nulo dependiendo si se ha encontrado el registro
     */
    public Usuario selectUsuarios(SQLiteDatabase db, String email) {
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Método que realiza una consulta para devolver un usuario a través del nombre del usuario proporcionado
     * @param db BBDD
     * @param nombreUsuario Nombre del usuario
     * @return Devuelve un objeto Usuario o un valor nulo dependiendo si se ha encontrado el registro
     */
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

    /**
     * Método que actualiza el campo correspondiente a la contraseña a través de una consulta personalizada
     * @param db BBDD
     * @param email Email del usuario
     * @param nuevaClave Nueva contraseña a modificar
     * @return Devuelve un entero para comprobar si se han realizado cambios en la BBDD
     */
    public int actualizarUsuarioClave(SQLiteDatabase db, String email, String nuevaClave) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("clave", Hash.md5(nuevaClave));
        resultado = db.update(TABLA_USUARIOS, valores, "email = ?", new String[]{email});
        return resultado;
    }

    /**
     * Método que actualiza el campo correspondiente al nombre de usuario a través de una consulta personalizada
     * @param db BBDD
     * @param usuario Nombre de usuario
     * @param nuevoNonbreUsuario Nuevo nombre de usuario
     * @return Devuelve un entero para comprobar si se han realizado cambios en la BBDD
     */
    public int actualizarNombreUsuario(SQLiteDatabase db, String usuario, String nuevoNonbreUsuario) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("nombreUsuario", nuevoNonbreUsuario);
        resultado = db.update(TABLA_USUARIOS, valores, "nombreUsuario = ?", new String[]{usuario});
        return resultado;
    }

    /**
     * Método que inserta un nuevo registro en la tabla de artista con los valores proporcionados
     * @param db BBDD
     * @param nuevoArtista Artista a insertar
     * @return Devuelve un entero para comprobar si se han realizado cambios en la BBDD
     */
    public int insertarArtista(SQLiteDatabase db, Artista nuevoArtista) {
        ContentValues valores = new ContentValues();
        valores.put("nombreArtistico", nuevoArtista.getNombreArtista());
        valores.put("nacionalidad", nuevoArtista.getNacionalidad());
        valores.put("fechaNacimiento", nuevoArtista.getFechaNacimiento());
        valores.put("foto", nuevoArtista.getFoto());
        db.insert(TABLA_ARTISTA, null, valores);
        return nuevoArtista.getIdArtista();
    }

    /**
     * Método que realiza una consulta para devolver un artista a través del nombre que se proporcione en el parámetro
     * @param db BBDD
     * @param nombreArtista Nombre del artista
     * @return Devuelve un objeto Artista o un valor nulo dependiendo si se ha encontrado el registro
     */
    public Artista obtenerIdArtistaPorNombre(SQLiteDatabase db, String nombreArtista) {
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_ARTISTA_NOMBRE, new String[]{nombreArtista});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreArtistico = cursor.getString(cursor.getColumnIndex("nombreArtistico"));
            @SuppressLint("Range") String nacionalidad = cursor.getString(cursor.getColumnIndex("nacionalidad"));
            @SuppressLint("Range") String fechaNacimiento = cursor.getString(cursor.getColumnIndex("fechaNacimiento"));
            @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
            return new Artista(nombreArtistico, nacionalidad, fechaNacimiento, foto);
        }
        cursor.close();
        return null;
    }

    /**
     * Método que inserta un nuevo registro en la tabla de evento con los valores proporcionados
     * @param db Conexión BBDD
     * @param nuevoEvento Evento a insertar
     * @return Devuelve un número entero para comprobar si se han realizado cambios en la BBDD
     */
    public int insertarEvento(SQLiteDatabase db, Evento nuevoEvento) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("nombreArtista", nuevoEvento.getNombreArtista());
        valores.put("precio", nuevoEvento.getPrecio());
        valores.put("sede", nuevoEvento.getSede());
        valores.put("pais", nuevoEvento.getPais());
        valores.put("fecha", nuevoEvento.getFecha());
        valores.put("foto", nuevoEvento.getFoto());
        valores.put("idArtista", nuevoEvento.getIdArtista());
        resultado = (int) db.insert(TABLA_EVENTOS, null, valores);
        return resultado;
    }

    /**
     * Método que realiza una consulta personalizada para obtener los 3 mayores registros de eventos en base a la fecha de inicio
     * @param db Conexión BBDD
     * @return Devuelve una lista de eventos
     */
    public ArrayList<Evento> selectEventosEntradas(SQLiteDatabase db) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_EVENTOS_DESTACADOS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(nombreArtista, precio, sede, pais, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    /**
     * Método que realiza una consulta personalizada para obtener los 3 mayores registros de eventos en base a la fecha de inicio
     * @param db Conexión BBDD
     * @return Devuelve una lista de eventos
     */
    public ArrayList<Evento> selectOtrosEventos(SQLiteDatabase db) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_OTROS_EVENTOS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(nombreArtista, precio, sede, pais, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    /**
     * Método que realiza una consulta personalizada para obtener un registro en base al nombre del artista y la fecha correspondiente
     * @param db Conexión BBDD
     * @param nombreArtistaConsulta Nombre del artista a consultar
     * @param fechaConsulta Fecha del evento
     * @return Devuelve una lista de eventos
     */
    public Evento selectEventosNombres(SQLiteDatabase db, String nombreArtistaConsulta, String fechaConsulta) {
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_EVENTOS_NOMBRES, new String[]{nombreArtistaConsulta, fechaConsulta});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idEvento = cursor.getInt(cursor.getColumnIndex("idEvento"));
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                return new Evento(idEvento,nombreArtista, precio, sede, pais, fecha, foto, idArtista);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return null;
    }

    /**
     * Método que realiza una consulta personalizada para obtener un registro en base al nombre del artista a consultar
     * @param db Conexión BBDD
     * @param nombreArtistaConsulta Nombre del artista a consultar
     * @return Devuelve una lista de eventos
     */
    public ArrayList<Evento> selectEventosNombresBuscar(SQLiteDatabase db, String nombreArtistaConsulta) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery(SENTENCIA_SELECCION_EVENTOS_NOMBRES_BUSCAR, new String[]{"%" + nombreArtistaConsulta + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idEvento = cursor.getInt(cursor.getColumnIndex("idEvento"));
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(idEvento,nombreArtista, precio, sede, pais, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    /**
     * Método que realiza una consulta personalizada para obtener todos los registros (eventos) relacionados con el idEvento pasado por parámetro
     * @param db Conexión BBDD
     * @param idEvento Id a consultar
     * @return Devuelve una lista de eventos
     */
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
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(idEventoS,nombreArtista, precio, sede, pais, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    /**
     * Método que realiza una consulta general para obtener todos los registros de la tabla evento
     * @param db Conexión BBDD
     * @return Devuelve una lista de eventos
     */
    public ArrayList<Evento> selectEventos(SQLiteDatabase db) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from evento", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idEventoS = cursor.getInt(cursor.getColumnIndex("idEvento"));
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(idEventoS,nombreArtista, precio, sede, pais, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    /**
     * Método que inserta un nuevo registro en la tabla de misEventos con los valores proporcionados
     * @param db Conexión BBDD
     * @param eventos Eventos a insertar
     * @return Devuelve un número entero para comprobar si se han realizado cambios en la BBDD
     */
    public int insertarEventoUsuario(SQLiteDatabase db, MisEventos eventos) {
        int resultado;
        ContentValues valores = new ContentValues();
        valores.put("idEvento", eventos.getIdEvento());
        valores.put("idUsuario", eventos.getIdUsuario());
        resultado = (int) db.insert(TABLA_MISEVENTOS, null, valores);
        return resultado;
    }

    /**
     * Método que actualiza el campo correspondiente al código de referencia a través de una consulta personalizada en la tabla misEventos
     * @param db BBDD
     * @param evento Evento a consultar
     * @param usuario Usuario de consultar
     * @return Devuelve un entero para comprobar si se han realizado cambios en la BBDD
     */
    public int actualizarCodReferenciaEvento(SQLiteDatabase db, Evento evento, Usuario usuario) {
        int resultado;
        ContentValues valores = new ContentValues();
        Random random = new Random();
        String codReferencia = String.valueOf(10000000 + random.nextInt(90000000));
        valores.put("codReferencia", codReferencia);
        resultado = db.update(TABLA_MISEVENTOS, valores, "idEvento = ? and idUsuario = ?", new String[]{String.valueOf(evento.getIdEvento()), String.valueOf(usuario.getIdUsuario())});
        return resultado;
    }

    /**
     * Método que realiza una consulta personalizada para obtener todos los registros de la tabla misEventos que se correspondan con el id del usuario actual
     * @param db Conexión BBDD
     * @param idUsuarioWhere Id del usuario a consultar
     * @return Devuelve una lista de misEventos
     */
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

    /**
     * Método que realiza una consulta general para obtener todos los registros de la tabla evento, en la que se ordenaran en base al campo proporcionado
     * @param db Conexión BBDD
     * @param metodoOrdenacion Campo a ordenar
     * @return Devuelve una lista de misEventos
     */
    public ArrayList<Evento> selectEventosOrdenacion(SQLiteDatabase db, String metodoOrdenacion) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Cursor cursor = null;
        if (!metodoOrdenacion.equals("precio")) {
            cursor = db.rawQuery("select * from evento order by " + metodoOrdenacion, null);
        } else {
            cursor = db.rawQuery("select * from evento order by " + metodoOrdenacion + " desc", null);
        }

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreArtista = cursor.getString(cursor.getColumnIndex("nombreArtista"));
                @SuppressLint("Range") double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                @SuppressLint("Range") String sede = cursor.getString(cursor.getColumnIndex("sede"));
                @SuppressLint("Range") String pais = cursor.getString(cursor.getColumnIndex("pais"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") int foto = cursor.getInt(cursor.getColumnIndex("foto"));
                @SuppressLint("Range") int idArtista = cursor.getInt(cursor.getColumnIndex("idArtista"));
                eventos.add(new Evento(nombreArtista, precio, sede, pais, fecha, foto, idArtista));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventos;
    }

    /**
     * Método que a través de una consulta personalizada, borra el registro de la tabla misEventos que se proporcione
     * @param db Conexión BBDD
     * @param misEventos Evento a borrar
     * @return Devuelve un entero para comprobar si se han realizado cambios en la BBDD
     */
    public int eliminarEventoUsuario(SQLiteDatabase db, MisEventos misEventos) {
        int resultado;
        resultado = db.delete(TABLA_MISEVENTOS, "idUsuario = ? and idEvento = ?", new String[]{String.valueOf(misEventos.getIdUsuario()),String.valueOf(misEventos.getIdEvento())});
        return resultado;
    }

    /**
     * Método que realiza una consulta personalizada para obtener registros de la tabla misEventos que se correspondan con el id del evento y el id del usuario proporcionado
     * @param db Conexión BBDD
     * @param idEventoWhere Id del evento a consultar
     * @param idUsuarioWhere Id del usuario a consultar
     * @return  una lista de misEventos
     */
    public MisEventos selectMisEventosCod(SQLiteDatabase db, int idEventoWhere, int idUsuarioWhere) {
        Cursor cursor = db.rawQuery("select * from misEventos where idEvento = ? and idUsuario = ?", new String[]{String.valueOf(idEventoWhere),String.valueOf(idUsuarioWhere)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int idEvento = cursor.getInt(cursor.getColumnIndex("idEvento"));
            @SuppressLint("Range") int idUsuario = cursor.getInt(cursor.getColumnIndex("idUsuario"));
            @SuppressLint("Range") String codReferencia = cursor.getString(cursor.getColumnIndex("codReferencia"));
            return new MisEventos(idEvento,idUsuario,codReferencia);
        }
        cursor.close();
        return null;
    }

}
