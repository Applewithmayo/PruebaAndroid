package com.example.appduoc

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHandler(context: Context) : SQLiteOpenHelper(context, "datos_db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = ("CREATE TABLE LOG (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "USUARIO TEXT)")
        db?.execSQL(sql)
        Log.i("TOV", "onCreate: Base de datos Ok")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertLogByLogin(usuario: String) : Long {
        val db = this.writableDatabase
        val contentvalues = ContentValues()

        contentvalues.put("USUARIO", usuario)

        val success = db.insert("LOG", null, contentvalues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun userExists(): Int {
        val sql = "SELECT COUNT(USUARIO) AS CANTIDAD FROM LOG"
        val db = this.readableDatabase
        var cantidad = 0
        val cursor = db.rawQuery(sql, null)

        try{
            if (cursor.moveToFirst()){
                cantidad = cursor.getInt(0)
            }
        }catch (e: SQLiteException) {
            db.execSQL(sql)
            Log.i("TOV", e.toString())
        }

        return cantidad
    }

    fun deleteUser() : Int {
        val db = this.writableDatabase

        val success = db.delete("LOG", null, null)
        db.close()
        return success
    }
}