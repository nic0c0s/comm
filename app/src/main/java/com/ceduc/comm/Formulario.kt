package com.ceduc.comm

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class Formulario : AppCompatActivity() {

    lateinit var basedatos:SQLiteDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        basedatos = SQLiteDB(this)

        val shPC = getSharedPreferences("shpCarro", Context.MODE_PRIVATE)
        val shPP = getSharedPreferences("shpProducto", Context.MODE_PRIVATE)

        val codigoTxt = (findViewById<EditText>(R.id.etCodigo))
        val descripcionTxt = findViewById<EditText>(R.id.etDescripcion)
        val precioTxt = findViewById<EditText>(R.id.etPrecio)

        val agregardatos = findViewById<Button>(R.id.btAgregarDatos)
        val actualizardatos = findViewById<Button>(R.id.btActualizarDatos)
        val borrardatos = findViewById<Button>(R.id.btBorrarDatos)
        val agregarcarro = findViewById<Button>(R.id.btAgregarAlCarro)
        val mostrardatos = findViewById<Button>(R.id.btMostrar)

        codigoTxt.setText(intent.extras?.getString("CODIGO", ""))

        agregardatos.setOnClickListener {
            val codigoSHPP = shPP.getString(codigoTxt.text.toString() + "codigo","")

            if (codigoTxt.text.isEmpty()){Toast.makeText(this,"Ingrese un codigo", Toast.LENGTH_SHORT).show()}
            else if (codigoSHPP!!.isNotEmpty())
            {Toast.makeText(this,"El producto ya existe, para actualizar datos use el otro boton", Toast.LENGTH_SHORT).show()}

            else if (codigoTxt.text.isNotEmpty() && descripcionTxt.text.isNotEmpty() && precioTxt.text.isNotEmpty()
                && TextUtils.isDigitsOnly(codigoTxt.text) &&  TextUtils.isDigitsOnly(precioTxt.text)){
                val codigoint = codigoTxt.text.toString().toInt()
                val descripcionstr = descripcionTxt.text.toString()
                val precioint = precioTxt.text.toString().toInt()
                basedatos.agregarDatos(codigoint, descripcionstr, precioint)

                val editorP = shPP.edit()

                editorP.putString(codigoint.toString()+"codigo", codigoint.toString())
                editorP.apply()

                Toast.makeText(this,"Producto agregado", Toast.LENGTH_SHORT).show()
            }
            else{Toast.makeText(this,"Ingrese los datos correctamente", Toast.LENGTH_SHORT).show()}

        }
        actualizardatos.setOnClickListener {
            val codigoSHPP = shPP.getString(codigoTxt.text.toString() + "codigo","")

            if (codigoTxt.text.isEmpty()){Toast.makeText(this, "Ingrese un código", Toast.LENGTH_LONG).show()}
            else if (codigoSHPP!!.isEmpty()){Toast.makeText(this, "El producto no existe, primero debe crearlo", Toast.LENGTH_LONG).show()}
            else if (descripcionTxt.text.isEmpty() or precioTxt.text.isEmpty() ) {
                Toast.makeText(this, "Ingrese datos para actualizar", Toast.LENGTH_LONG).show()}
            else if (descripcionTxt.text!!.isNotEmpty() && codigoTxt.text.isNotEmpty() && precioTxt.text.isNotEmpty()){
                val codigoint = codigoTxt.text.toString().toInt()
                val descripcionstr = descripcionTxt.text.toString()
                val precioint = precioTxt.text.toString().toInt()

                basedatos.actualizarDatos(codigoint, descripcionstr, precioint)
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_LONG).show()

                val editor = shPC.edit()
                editor.putString(codigoint.toString()+"codigo", codigoint.toString())
                editor.putString(codigoint.toString()+"descripcion", descripcionstr)
                editor.apply()}
            else{Toast.makeText(this, "Hubo un error", Toast.LENGTH_LONG).show()}
        }
        borrardatos.setOnClickListener {
            val codigoSHPP = shPP.getString(codigoTxt.text.toString() + "codigo","")

            if (codigoTxt.text.isEmpty()) {Toast.makeText(this,"Ingrese el codigo del producto a borrar", Toast.LENGTH_SHORT).show()}
            else if (codigoSHPP!!.isEmpty()){Toast.makeText(this,"El producto no existe", Toast.LENGTH_SHORT).show()}
            else if (codigoTxt.text.isNotEmpty() && codigoSHPP!!.isNotEmpty()){
                val codigoint = codigoTxt.text.toString().toInt()
                basedatos.borrarDatos(codigoint)
                Toast.makeText(this, "Producto borrado",Toast.LENGTH_SHORT).show()

                val editor = shPC.edit()
                val editorP = shPP.edit()

                editor.putString(codigoint.toString()+"codigo", "")
                editor.putString(codigoint.toString()+"descripcion", "")
                editor.apply()

                editorP.putString(codigoint.toString()+"codigo", "")
                editorP.apply()

                codigoTxt.setText("")
                precioTxt.setText("")
                descripcionTxt.setText("")
            }

            else{Toast.makeText(this,"Hubo algun error", Toast.LENGTH_SHORT).show()}
        }
        agregarcarro.setOnClickListener {
            val codigoSHPP = shPP.getString(codigoTxt.text.toString() + "codigo","")

            if (codigoTxt.text.isEmpty() && descripcionTxt.text.isEmpty() && precioTxt.text.isEmpty())
                {Toast.makeText(this,"No hay datos en los campos", Toast.LENGTH_SHORT).show()}
            else if (codigoSHPP!!.isEmpty())
                {Toast.makeText(this,"No existe producto con ese codigo para agregarse al carro", Toast.LENGTH_SHORT).show()}
            else if (codigoSHPP.isNotEmpty()){
                val codigoStr = codigoTxt.text.toString()
                val descripcionStr = descripcionTxt.text.toString()

                val editor = shPC.edit()

                editor.putString(codigoStr+"codigo", codigoStr)
                editor.putString(codigoStr+"descripcion", descripcionStr)
                editor.apply()
                Toast.makeText(this,"Producto agregado al carro", Toast.LENGTH_SHORT).show()}

        }

        mostrardatos.setOnClickListener {
            val db:SQLiteDatabase = basedatos.readableDatabase
            val codigoStr = codigoTxt.text.toString()

            val codigoSHPP = shPP.getString(codigoTxt.text.toString() + "codigo","")


            val cursorP = db.rawQuery("SELECT descripcion, precio FROM producto WHERE codigo = ?", arrayOf(codigoStr))

            if (cursorP.moveToFirst()){
                val precioC = cursorP.getInt(cursorP.getColumnIndex("precio"))
                val descripcionC = cursorP.getString(cursorP.getColumnIndex("descripcion"))

                precioTxt.setText(precioC.toString())
                descripcionTxt.setText(descripcionC)

            }
            else if (codigoSHPP!!.isEmpty()){
                Toast.makeText(this, "Código no reconocido", Toast.LENGTH_LONG).show()
            }
            else{Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()}
            cursorP.close()
            db.close()
        }
    }
}