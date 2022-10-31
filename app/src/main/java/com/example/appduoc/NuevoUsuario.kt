package com.example.appduoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class NuevoUsuario : AppCompatActivity() {
    private var _isCreateSuccess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_usuario)
    }

    fun crearNuevoUsuario(view: View){
        if (_isCreateSuccess) {
            finish()
            return
        } else {
            var button = findViewById<Button>(R.id.btn_nuevo_usuario)
            val correo = findViewById<EditText>(R.id.txt_correo).text.toString()
            val contrasena = findViewById<EditText>(R.id.txt_contrasena).text.toString()
            val nombre = findViewById<EditText>(R.id.txt_nombre).text.toString()
            val apellido = findViewById<EditText>(R.id.txt_apellido).text.toString()

            val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
            val json =
                "{\"nombreFuncion\" : \"UsuarioAlmacenar\", \"parametros\": [\"$correo\", \"$contrasena\", \"$nombre\", \"$apellido\"]}"
            val client = OkHttpClient()
            val body: RequestBody = RequestBody.create(mediaType, json)
            val request: Request =
                Request.Builder().url("https://fer-sepulveda.cl/API_PRUEBA2/api-service.php").post(body).build()

            val toastSuccess =
                Toast.makeText(this@NuevoUsuario,"Usuario creado correctamente", Toast.LENGTH_LONG)

            val toastError =
                Toast.makeText(this@NuevoUsuario,"Ha ocurrido un inconveniente, intente con otro usuario", Toast.LENGTH_LONG)

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("TOV: ERROR: " + e.message)
                    return
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        val jsonData = response.body()!!.string()
                        val obj = Json.decodeFromString<Respuesta>(jsonData.toString())
                        var response = obj.result[0].RESPUESTA.trim()
                        if (response != "ERR01")
                        {
                            toastSuccess.show()
                            button.setText("Volver")
                            _isCreateSuccess = true
                            limpiarCasillas()
                        } else {
                            toastError.show()
                        }
                    }
                }
            })
        }
    }

    fun limpiarCasillas(){
        var inputCorreo = findViewById<EditText>(R.id.txt_correo)
        var inputContrasena = findViewById<EditText>(R.id.txt_contrasena)
        var inputNombre = findViewById<EditText>(R.id.txt_nombre)
        var inputApellido = findViewById<EditText>(R.id.txt_apellido)
        inputCorreo.setText("")
        inputCorreo.isEnabled = false
        inputContrasena.setText("")
        inputContrasena.isEnabled = false
        inputNombre.setText("")
        inputNombre.isEnabled = false
        inputApellido.setText("")
        inputApellido.isEnabled = false
    }
}