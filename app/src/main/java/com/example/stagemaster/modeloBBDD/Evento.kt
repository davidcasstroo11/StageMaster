package com.example.stagemaster.modeloBBDD


data class Evento(
    val nombreArtista: String,
    val precio: Double,
    val sede: String,
    val pais: String,
    val entradas: Int,
    val fecha: String,
    val foto: Int,
    val idArtista: Int
):java.io.Serializable