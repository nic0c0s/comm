package com.ceduc.comm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class SQLiteDB (context: Context):SQLiteOpenHelper(context,"productos", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE producto (codigo INT PRIMARY KEY, descripcion VARCHAR(40), precio INT(8));"
        db?.execSQL(query)

    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var dropQuery = "DROP TABLE IF EXISTS Producto;"
        db?.execSQL(dropQuery)

    }

    fun agregarDatos(codigo:Int,descripcion:String,precio:Int){
        val edDb = this.writableDatabase
        val inventarioProductos = ContentValues()
        inventarioProductos.put("codigo", codigo)
        inventarioProductos.put("descripcion", descripcion)
        inventarioProductos.put("precio", precio)
        edDb.insert("Producto",null,inventarioProductos)
        edDb.close()
    }

    fun actualizarDatos(codigo:Int,descripcion:String,precio:Int){
        val db:SQLiteDatabase = this.writableDatabase
        val inventarioProductos = ContentValues()
        inventarioProductos.put("descripcion", descripcion)
        inventarioProductos.put("precio", precio)

        db.update("Producto", inventarioProductos, "codigo = ?", arrayOf(codigo.toString()))

        }

    fun borrarDatos(codigo:Int){
        val db:SQLiteDatabase = this.writableDatabase
        db.delete("Producto", "codigo = ?", arrayOf(codigo.toString()))
    }




}