package com.example.stagemaster.modeloBBDD;


public class Evento {
    int idEvento;
    String nombreArtista;
    Double precio;
    String sede;
    String pais;
    String fecha;
    int foto;
    int idArtista;

    public Evento(String nombreArtista, Double precio, String sede, String pais, String fecha, int foto, int idArtista) {
        this.nombreArtista = nombreArtista;
        this.precio = precio;
        this.sede = sede;
        this.pais = pais;
        this.fecha = fecha;
        this.foto = foto;
        this.idArtista = idArtista;
    }

    public Evento(int idEvento, String nombreArtista, Double precio, String sede, String pais, String fecha, int foto, int idArtista) {
        this.idEvento = idEvento;
        this.nombreArtista = nombreArtista;
        this.precio = precio;
        this.sede = sede;
        this.pais = pais;
        this.fecha = fecha;
        this.foto = foto;
        this.idArtista = idArtista;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getFoto() {
        return foto;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public String getPais() {
        return pais;
    }

    public String getSede() {
        return sede;
    }
}