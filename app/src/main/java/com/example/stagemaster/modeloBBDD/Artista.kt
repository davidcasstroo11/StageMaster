package com.example.stagemaster.modeloBBDD

import java.util.Date

data class Artista (
    val nombreArtistico: String,
    val nacionalidad: String,
    val fechaNacimiento: Date,
    val foto: Int
):java.io.Serializable