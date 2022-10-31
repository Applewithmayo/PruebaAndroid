package com.example.appduoc

import kotlinx.serialization.Serializable

@Serializable
class Registro(
    var ID: String,
    var PATENTE: String,
    var MARCA: String,
    var COLOR: String,
    var FECHA_INGRESO: String,
    var KILOMETRAJE: String,
    var MOTIVO: String,
    var MOTIVO_TEXTO: String?,
    var RUT_cLIENTE: String,
    var NOMBRE_CLIENTE: String,
    var CORREO_INSPECTOR: String)

