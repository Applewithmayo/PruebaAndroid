package com.example.appduoc

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity,
                    private val id: Array<String>,
                    private val patente: Array<String>,
                    private val marca: Array<String>,
                    private val color: Array<String>,
                    private val fecha_ingreso: Array<String>,
                    private val kilometraje: Array<String>,
                    private val motivo: Array<String>,
                    private val motivo_texto: Array<String>,
                    private val rut_cliente: Array<String>,
                    private val nombre_cliente: Array<String>,
                    private val correo_inspector: Array<String>,
                    )
    : ArrayAdapter<String>(context, R.layout.custom_list, id) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val id  = rowView.findViewById(R.id.txt_id) as TextView
        val patente  = rowView.findViewById(R.id.txt_patente) as TextView
        val marca  = rowView.findViewById(R.id.txt_marca) as TextView
        val color = rowView.findViewById(R.id.txt_color) as TextView
        val fecha_ingreso = rowView.findViewById(R.id.txt_fecha_ingreso) as TextView
        val kilometraje = rowView.findViewById(R.id.txt_kilometraje) as TextView
        val motivo = rowView.findViewById(R.id.txt_motivo) as TextView
        val motivo_texto = rowView.findViewById(R.id.txt_motivo_texto) as TextView
        val rut_cliente = rowView.findViewById(R.id.txt_rut_cliente) as TextView
        val nombre_cliente = rowView.findViewById(R.id.txt_nombre_cliente) as TextView
        val correo_inspector = rowView.findViewById(R.id.txt_correo_inspector) as TextView

        id.text = "MARCA: ${this.id[position]}"
        patente.text = "PATENTE: ${this.patente[position]}"
        marca.text = "MARCA: ${this.marca[position]}"
        color.text = "COLOR: ${this.color[position]}"
        fecha_ingreso.text = "FECHA INGRESO: ${this.fecha_ingreso[position]}"
        kilometraje.text = "KILOMETRAJE: ${this.kilometraje[position]}"
        motivo.text = "MOTIVO: ${this.motivo[position]}"
        motivo_texto.text = "MOTIVO TEXTO: ${this.motivo_texto[position]}"
        rut_cliente.text = "RUT CLIENTE: ${this.rut_cliente[position]}"
        nombre_cliente.text = "NOMBRE CLIENTE: ${this.nombre_cliente[position]}"
        correo_inspector.text = "CORREO INSPECTOR: ${this.correo_inspector[position]}"

        return rowView
    }
}