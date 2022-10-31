package com.example.appduoc

import kotlinx.serialization.Serializable

@Serializable
class Usuario(
    var CORREO: String,
    var CONTRASENA: String,
    val NOMBRE: String,
    val APELLIDO: String)
