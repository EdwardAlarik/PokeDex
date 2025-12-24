package com.edwardalarik.app.api.db

import android.database.sqlite.SQLiteDatabase
import com.edwardalarik.app.Log

class DBHelper {
    fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()

        // Creacion de tablas
        tableMove(db)
        tableType(db)
        tableAbility(db)
        tablePokemon(db)

        db.setTransactionSuccessful()
        db.endTransaction()
    }

    private fun tableAbility(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Habilidades")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_ABILITY} (
                ${DB.COL_ID_ABILITY} INTEGER,
                ${DB.COL_NAME} TEXT, 
                PRIMARY KEY (${DB.COL_ID_ABILITY})
            )
        """.trimIndent()
        )
    }

    private fun tableType(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Tipos")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_TYPE} (
                ${DB.COL_ID_TYPE} INTEGER,
                ${DB.COL_NAME} TEXT, 
                PRIMARY KEY (${DB.COL_ID_TYPE})
            )
        """.trimIndent()
        )
    }

    private fun tableMove(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Movimientos")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_MOVE} (
                ${DB.COL_ID_MOVE} INTEGER,
                ${DB.COL_NAME} TEXT, 
                PRIMARY KEY (${DB.COL_ID_MOVE})
            )
        """.trimIndent()
        )
    }

    private fun tablePokemon(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Pokemon")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_POKEMON} (
                ${DB.COL_ID_POKEMON} INTEGER,
                ${DB.COL_ORDER} INTEGER,
                ${DB.COL_NAME} TEXT,
                ${DB.COL_TYPES} TEXT,
                ${DB.COL_ABILITIES} TEXT,
                ${DB.COL_MOVES} TEXT,
                ${DB.COL_HEIGHT} INTEGER,
                ${DB.COL_WEIGHT} INTEGER, 
                ${DB.COL_BASE_EXPERIENCE} INTEGER, 
                ${DB.COL_IS_DEFAULT} INTEGER,
                ${DB.COL_LOCATION_AREA_ENCOUNTERS} TEXT, 
                PRIMARY KEY (${DB.COL_ID_POKEMON})
            )
        """.trimIndent()
        )
    }

    fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.beginTransaction()

        for (version in (oldVersion + 1)..newVersion) {
            try {
                when (version) {
                    1 -> {
                        onCreate(db)
                    }

                    2 -> {
                        onCreate(db)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        db.setTransactionSuccessful()
        db.endTransaction()
    }
}