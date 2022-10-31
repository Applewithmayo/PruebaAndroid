package com.example.appduoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseHandler: DbHandler = DbHandler(this)
        var usuarioExiste = databaseHandler.userExists()

        if (usuarioExiste == 1){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    fun login(view: View) {
        val usuario = findViewById<EditText>(R.id.txt_correo).text.toString();
        val contrasena = findViewById<EditText>(R.id.txt_contrasena).text.toString();

        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val json =
            "{\"nombreFuncion\" : \"UsuarioLogin\", \"parametros\": [\"$usuario\", \"$contrasena\"]}"
        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =
            Request.Builder().url("https://fer-sepulveda.cl/API_PRUEBA2/api-service.php").post(body).build()

        val toastError =
            Toast.makeText(this,"Credenciales invalidas", Toast.LENGTH_LONG)

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("TOV: ERROR: " + e.message)
                return
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val jsonData = response.body()!!.string()
                    val obj = Json.decodeFromString<RespuestaLogin>(jsonData.toString())
                    var response = obj.result
                    if (response != "LOGIN NOK")
                    {
                        val databaseHandler: DbHandler = DbHandler(this@MainActivity)
                        databaseHandler.insertLogByLogin(usuario)

                        val intent = Intent(this@MainActivity, Home::class.java);

                        val bundle = Bundle();
                        bundle.putString("usuario", usuario);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        toastError.show()
                    }
                }
            }
        })
    }

    fun navegarNuevoUsuario(view: View) {
        val intent = Intent(this, NuevoUsuario::class.java)
        startActivity(intent)
    }
}