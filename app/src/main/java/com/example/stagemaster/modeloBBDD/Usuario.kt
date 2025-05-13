package com.example.stagemaster.modeloBBDD

data class Usuario(
    val nombre: String,
    val apellidos: String,
    val nombreUsuario: String,
    val email: String,
    val clave: String
):java.io.Serializable