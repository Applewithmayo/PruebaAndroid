package com.example.appduoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class ListarRegistros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_registros)
        obtenerRegistros()
    }

    fun obtenerRegistros() {
        val client = OkHttpClient()

        val request: Request =
            Request.Builder().url("https://fer-sepulveda.cl/API_PRUEBA2/api-service.php?nombreFuncion=InspeccionObtener").get().build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("TOV: ERROR")
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()
                println("TOV: aca")
                val obj = Json.decodeFromString<Listar>(jsonData.toString())
                println("TOV : " + obj.result);

                runOnUiThread {
                    viewRecord(obj.result)
                }
            }
        })
    }

    fun viewRecord(lista: List<Registro>){
        val registro: List<Registro> = lista

        val ID = Array<String>(registro.size){ "0"}
        val PATENTE = Array<String>(registro.size){ "0"}
        val MARCA = Array<String>(registro.size){ "null"}
        val COLOR = Array<String>(registro.size){"null"}
        val FECHA_INGRESO = Array<String>(registro.size){"null"}
        val KILOMETRAJE = Array<String>(registro.size){"null"}
        val MOTIVO = Array<String>(registro.size){"null"}
        val MOTIVO_TEXTO = Array<String>(registro.size){"null"}
        val RUT_CLIENTE = Array<String>(registro.size){"null"}
        val NOMBRE_CLIENTE = Array<String>(registro.size){"null"}
        val CORREO_INSPECTOR = Array<String>(registro.size){"null"}
        var index = 0

        for(r in registro){
            ID[index] = r.ID.toString()
            PATENTE[index] = r.PATENTE.toString()
            MARCA[index] = r.MARCA.toString()
            COLOR[index] = r.COLOR.toString()
            FECHA_INGRESO[index] = r.FECHA_INGRESO.toString()
            KILOMETRAJE[index] = r.KILOMETRAJE.toString()
            MOTIVO[index] = r.MOTIVO.toString()
            MOTIVO_TEXTO[index] = r.MOTIVO_TEXTO.toString()
            RUT_CLIENTE[index] = r.RUT_cLIENTE.toString()
            NOMBRE_CLIENTE[index] = r.NOMBRE_CLIENTE.toString()
            CORREO_INSPECTOR[index] = r.CORREO_INSPECTOR.toString()
            index++
        }

        var listView = findViewById<ListView>(R.id.listView)

        val myListAdapter = MyListAdapter(
            this,
                    ID,
                    PATENTE,
                    MARCA,
                    COLOR,
                    FECHA_INGRESO,
                    KILOMETRAJE,
                    MOTIVO,
                    MOTIVO_TEXTO,
                    RUT_CLIENTE,
                    NOMBRE_CLIENTE,
                    CORREO_INSPECTOR)
        listView.adapter = myListAdapter
    }
}