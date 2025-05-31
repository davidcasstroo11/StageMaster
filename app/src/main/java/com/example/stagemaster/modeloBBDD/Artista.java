package com.example.stagemaster.modeloBBDD;

import java.util.Date;

public class Artista {
    int idArtista;
    String nombreArtista;
    String nacionalidad;
    Date fechaNacimiento;
    int foto;

    public Artista(int idArtista, String nombreArtista, String nacionalidad, Date fechaNacimiento, int foto) {
        this.idArtista = idArtista;
        this.nombreArtista = nombreArtista;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.foto = foto;
    }

    public Artista(String nombreArtista, String nacionalidad, Date fechaNacimiento, int foto) {
        this.nombreArtista = nombreArtista;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.foto = foto;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public int getFoto() {
        return foto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }

}