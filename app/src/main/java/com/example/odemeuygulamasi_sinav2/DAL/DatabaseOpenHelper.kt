package com.example.odemeuygulamasi_sinav2.DAL

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context, name:String, factory: SQLiteDatabase.CursorFactory?, version:Int ) : SQLiteOpenHelper(context,name,factory,version){
    override fun onCreate(p0: SQLiteDatabase) {
        val sorgu = "CREATE TABLE OdemeTipi(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Baslik TEXT, OdemePeriyodu TEXT, PeriyotGunu INTEGER)"
        val sorgu2 = "CREATE TABLE Odeme(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, OdemeTutari REAL, OdemeTarihi TEXT, OdemeTipiFK INTEGER,  FOREIGN KEY(OdemeTipiFK) REFERENCES OdemeTipi(Id))"

        p0.execSQL(sorgu)
        p0.execSQL(sorgu2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}