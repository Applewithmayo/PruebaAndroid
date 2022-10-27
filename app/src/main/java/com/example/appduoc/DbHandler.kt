package com.example.appduoc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHandler(context: Context) : SQLiteOpenHelper(context, "datos_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = ("CREATE TABLE LOG (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "USER_FULL_NAME TEXT, DATETIME_LOGIN REAL DEFAULT (datetime('now', 'localtime')")
        db?.execSQL(sql)
        Log.i("STP", "onCreate: Base de datos Ok")
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertLogByLogin() : Long {
        val db = this.writableDatabase
        val contentvalues = ContentValues()

        contentvalues.put("USER_FULL_NAME", "TOVERPIL ASTETE NUÑEZ")
        val success = db.insert("LOG", null, ContentValues())
        db.close()

        return success
    }
}