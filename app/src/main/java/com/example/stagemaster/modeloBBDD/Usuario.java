package com.example.stagemaster.modeloBBDD;

public class Usuario {
    int idUsuario;
    String nombre;
    String apellidos;
    String nombreUsuario;
    String email;
    String clave;

    public Usuario(String nombre, String apellidos, String nombreUsuario, String email, String clave) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.clave = clave;
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String nombreUsuario, String email, String clave) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.clave = clave;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getClave() {
        return clave;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}