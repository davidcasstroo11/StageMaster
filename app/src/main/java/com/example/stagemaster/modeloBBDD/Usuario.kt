package com.example.stagemaster.modeloBBDD

data class Usuario(
    val nombre: String,
    val apellidos: String,
    val nombreUsuario: String,
    val clave: String,
    val email: String
):java.io.Serializable