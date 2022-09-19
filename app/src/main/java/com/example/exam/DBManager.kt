package com.example.exam

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DBManager(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table results (id integer primary key, url text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("drop table if exists results")
        onCreate(db)
    }


    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }


    @SuppressLint("Range")
    fun getData(): ArrayList<Result> {

        val data: ArrayList<Result> = ArrayList()

        val db = this.writableDatabase

        val query = "SELECT * FROM  " + "results"

        val cursor: Cursor?


        try {
            cursor = db.rawQuery(query, null)
        } catch (
            e: Exception
        ) {
            e.printStackTrace()
            return ArrayList()
        }

        var url: String


        if (cursor.moveToFirst()) {

            do {
                url = cursor.getString(cursor.getColumnIndex("url"))

                data.add(
                    Result(
                        url,
                        -1, -1, -1, -1, -1, -1
                    )
                )

            } while (cursor.moveToNext())

        }

        return data

    }


    fun deleteDB(context: Context) {
        context.deleteDatabase(DATABASE_NAME)

    }

    fun deleteEntry(url: String){

        val db = this.writableDatabase

        db.delete("results ", "url = ?", arrayOf(url))
    }

    fun add(url: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("url", url)
        val success = db.insert("results", null, contentValues)

        return success

    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ResultManager.db"
    }
}