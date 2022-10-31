package com.example.appduoc

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class
Home : AppCompatActivity() {

    var usuario = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val extras = intent.extras;

        if(extras != null) {
            usuario = extras.getString("usuario").toString();
            this.supportActionBar!!.title = "Bienvenido: " + usuario;
        }

        val sw_otro_active = findViewById<RadioButton>(R.id.opcion_otro)
        val txt_motivo_active = findViewById<EditText>(R.id.motivo_text)
        sw_otro_active.isChecked = false
        txt_motivo_active.isEnabled = false

        iniciarFormulario();

    }

    fun moveSwitchOtro(view: View) {
        val sw_otro_active = findViewById<RadioButton>(R.id.opcion_otro)
        val txt_motivo_active = findViewById<EditText>(R.id.motivo_text)
        txt_motivo_active.isEnabled = sw_otro_active.isChecked
        txt_motivo_active.setText("")
    }



    fun iniciarFormulario(){
        var marca_opc = arrayOf("Suzuki", "Fiat", "Mercedes-Benz", "Chevrolet", "Audi")
        var adapter_marca = ArrayAdapter (this, android.R.layout.simple_spinner_item, marca_opc)
        adapter_marca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        var spinnerMarca = findViewById<Spinner>(R.id.spinner_marca)
        spinnerMarca.adapter = adapter_marca;

        var color_opc = arrayOf("Azul", "Amarillo", "Negro", "Verde", "Rojo", "blanco")
        var adapter_color = ArrayAdapter (this, android.R.layout.simple_spinner_item, color_opc)
        adapter_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        var spinnerColor = findViewById<Spinner>(R.id.spinner_color)
        spinnerColor.adapter = adapter_color;

    }

    fun finalizarReporte (view: View){
        val builder = AlertDialog.Builder(this);
        val marca_opc = findViewById<Spinner>(R.id.spinner_marca);
        var color_opc = findViewById<Spinner>(R.id.spinner_color);
        var validador = true;

        val nombre = findViewById<EditText>(R.id.nombre_text).text.toString();  //obligatorio
        if (nombre == "") {
            validador = false
            val toast = Toast.makeText(this, "Debe indicar un nombre", Toast.LENGTH_LONG);
            toast.show();
        }

        val rut = findViewById<EditText>(R.id.rut_text).text.toString();  //obligatorio
        if (rut == "") {
            validador = false
            val toast = Toast.makeText(this, "Debe indicar un rut", Toast.LENGTH_LONG);
            toast.show();
        }

        val fecha = findViewById<EditText>(R.id.fecha_TextDate).text.toString();  //obligatorio
        if (fecha == "") {
            validador = false
            val toast = Toast.makeText(this, "Debe indicar un la fecha de ingreso", Toast.LENGTH_LONG);
            toast.show();
        }

        val kilometraje = findViewById<EditText>(R.id.kilometraje_textNumber).text.toString();
        if (kilometraje == "") {
            validador = false
            val toast =
                Toast.makeText(this, "Debe indicar un la fecha de ingreso", Toast.LENGTH_LONG);
            toast.show();
        }

        val razon = findViewById<EditText>(R.id.motivo_text).text.toString();


        val marca = marca_opc.selectedItem.toString();
        val color = color_opc.selectedItem.toString();

        var patente = findViewById<EditText>(R.id.patente_text).text.toString();
        if (patente == "") {
            patente = "No indica"
        }

        var motivo ="";

        try {
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup);
            val radioSeleccionadoID = radioGroup.checkedRadioButtonId;
            val radioSeleccionado = findViewById<RadioButton>(radioSeleccionadoID);
            motivo = radioSeleccionado.text.toString();
        } catch (err: Exception) {
            validador = false
            val toast =
                Toast.makeText(this, "Debe indicar un motivo", Toast.LENGTH_LONG);
            toast.show();
        }

        if(validador) {
            var usuario = this.usuario
            val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
            val json =
                "{\"nombreFuncion\":\"InspeccionAlmacenar\", \"parametros\": [\"$patente\", \"$marca\", \"$color\", \"$fecha\", \"$kilometraje\", \"$motivo\", \"$motivo\", \"$rut\", \"$nombre\", \"$usuario\"]}"
            val client = OkHttpClient()
            println("TOV " + json)
            val body: RequestBody = RequestBody.create(mediaType, json)
            val request: Request =
                Request.Builder().url("https://fer-sepulveda.cl/API_PRUEBA2/api-service.php").post(body).build()

            val toastError =
                Toast.makeText(this,"Ha ocurrido un inconveniente", Toast.LENGTH_LONG)

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

                        println("TOV: " + response)
                    }
                }
            })

            with(builder)
            {
                setTitle("Resumen")
                setMessage("Nombre completo: " + nombre + "\n" + "Rut: " + rut + "\n" + "Patente: " + patente + "\n" + "Marca: " + marca + "\n" + "Color: " + color + "\n" + "Fecha de ingreso: " + fecha + "\n" + "Kilometraje: " + kilometraje + "\n" + "Motivo: " + motivo + "\n" + "Razon: " + razon);
                setPositiveButton("Enviar", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton("Cancelar", negativeButtonClick)
                show()
            }
        }

    }

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,
            "Registro enviado correctamente", Toast.LENGTH_SHORT).show()
    }

    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,
            "Registro no enviado", Toast.LENGTH_SHORT).show()
    }

    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,
            "Tal Vez", Toast.LENGTH_SHORT).show()
    }

    fun navegarListarRegistros(view: View){
        val intent = Intent(this, ListarRegistros::class.java)
        startActivity(intent)
    }

    fun cerrarSesion(view: View){
        val databaseHandler: DbHandler = DbHandler(this)
        databaseHandler.deleteUser()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}