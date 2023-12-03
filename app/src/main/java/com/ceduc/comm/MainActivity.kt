package com.ceduc.comm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDron = findViewById<ImageButton>(R.id.bDron1)
        val btnNote = findViewById<ImageButton>(R.id.bNote2)
        val btnAudi = findViewById<ImageButton>(R.id.bAudi3)
        val btnVR = findViewById<ImageButton>(R.id.bVR4)

        val btnCarro = findViewById<Button>(R.id.bVerCarro)
        val btnListar = findViewById<Button>(R.id.bListar)

        val listaCa = findViewById<TextView>(R.id.txtProd)

        btnDron.setOnClickListener {
            val intentMain = Intent(this, Formulario::class.java)
            intentMain.putExtra("CODIGO", "111")
            startActivity(intentMain)
        }
        btnNote.setOnClickListener {
            val intentMain = Intent(this, Formulario::class.java)
            intentMain.putExtra("CODIGO", "112")
            startActivity(intentMain)
        }
        btnAudi.setOnClickListener {
            val intentMain = Intent(this, Formulario::class.java)
            intentMain.putExtra("CODIGO", "113")
            startActivity(intentMain)
        }
        btnVR.setOnClickListener {
            val intentMain = Intent(this, Formulario::class.java)
            intentMain.putExtra("CODIGO", "114")
            startActivity(intentMain)
        }
        btnCarro.setOnClickListener {
            val intentMainC = Intent(this, Carro::class.java)
            startActivity(intentMainC)
        }
        btnListar.setOnClickListener {
            val shPC = getSharedPreferences("shpCarro", Context.MODE_PRIVATE)
            val keys = shPC.all.keys
            val listaPCD = mutableListOf<String>()


            for (key in keys) {
                if (key.endsWith("descripcion")) {
                    val descripcion = shPC.getString(key, "")
                    val datos = descripcion
                    listaPCD.add(datos.toString())}}

            val datosD = listaPCD.joinToString("\n")
            listaCa.setText(datosD)
        }
    }
}