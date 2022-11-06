package com.example.textrecognizerkotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "history.db"
        private const val TBL_HISTORY = "tbl_history"
        private const val ID = "id"
        private const val TEXT = "text"
        private const val TIME = "time"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableText =
            ("CREATE TABLE $TBL_HISTORY($ID INTEGER PRIMARY KEY,$TEXT text,$TIME text)")
        p0?.execSQL(createTableText)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_HISTORY")
        onCreate(p0)
    }

    fun insertText(txt: HistoryModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, txt.id)
        contentValues.put(TEXT, txt.text)
        contentValues.put(TIME, txt.time)
        val success = db.insert(TBL_HISTORY, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllTexts(): ArrayList<HistoryModel> {
        val txtList: ArrayList<HistoryModel> = ArrayList()
        val selectQuery = "SELECT * from $TBL_HISTORY"
        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: java.lang.Exception) {

            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var text: String
        var time: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                text = cursor.getString(cursor.getColumnIndex("text"))
                time = cursor.getString(cursor.getColumnIndex("time"))
                val text = HistoryModel(id = id, text = text, time = time)
                txtList.add(text)
            } while (cursor.moveToNext())
        }
        return txtList
    }

    fun deleteHistoryByID(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)
        val success = db.delete(TBL_HISTORY, "id=$id", null)
        db.close()
        return success
    }
}