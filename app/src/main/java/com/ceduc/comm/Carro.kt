package com.ceduc.comm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView

class Carro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        val carrodes = findViewById<TextView>(R.id.tvDesC)

        val shPC = getSharedPreferences("shpCarro", Context.MODE_PRIVATE)

        val keys = shPC.all.keys
        val listaPCD = mutableListOf<String>()


        for (key in keys) {
            if (key.endsWith("descripcion")) {
                val descripcion = shPC.getString(key, "")
                val datos = descripcion
                listaPCD.add(datos.toString())}}

        val datosD = listaPCD.joinToString("\n")
        carrodes.setText(datosD)
        }

}