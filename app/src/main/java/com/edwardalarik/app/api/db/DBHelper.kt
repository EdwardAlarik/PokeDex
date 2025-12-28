package com.edwardalarik.app.api.db

import android.database.sqlite.SQLiteDatabase
import com.edwardalarik.app.Log

class DBHelper {
    fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()

        // Catalogos
        tableMove(db)
        tableType(db)
        tableAbility(db)

        // Pokemon
        tablePokemon(db)
        tableAbilitiesPokemon(db)
        tableStatsPokemon(db)
        tableTypesPokemon(db)

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
                ${DB.COL_DESCRIPTION} TEXT,
                ${DB.COL_CRIES_LATEST} TEXT,
                ${DB.COL_CRIES_LEGACY} TEXT,
                ${DB.COL_SPECIES} TEXT,
                ${DB.COL_HEIGHT} INTEGER,
                ${DB.COL_WEIGHT} INTEGER, 
                ${DB.COL_BASE_EXPERIENCE} INTEGER, 
                ${DB.COL_IS_DEFAULT} INTEGER,
                ${DB.COL_LOCATION_AREA_ENCOUNTERS} TEXT,
                ${DB.COL_FAV} INTEGER DEFAULT 0,
                PRIMARY KEY (${DB.COL_ID_POKEMON})
            )
        """.trimIndent()
        )
    }

    private fun tableAbilitiesPokemon(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Pokemon Abilidades")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_POKEMON_ABILITIES} (
                ${DB.COL_ID_POKEMON} INTEGER,
                ${DB.COL_NAME} TEXT,
                ${DB.COL_IS_HIDDEN} INTEGER,
                ${DB.COL_SLOT} INTEGER
            )
        """.trimIndent()
        )
    }

    private fun tableStatsPokemon(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Pokemon Stats")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_POKEMON_STATS} (
                ${DB.COL_ID_POKEMON} INTEGER,
                ${DB.COL_NAME} TEXT,
                ${DB.COL_BASE_STAT} INTEGER,
                ${DB.COL_EFFORT} INTEGER
            )
        """.trimIndent()
        )
    }

    private fun tableTypesPokemon(db: SQLiteDatabase) {
        Log.d("DBHelper", "Creando tabla Pokemon Tipos")
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ${DB.TAB_POKEMON_TYPES} (
                ${DB.COL_ID_POKEMON} INTEGER,
                ${DB.COL_NAME} TEXT,
                ${DB.COL_SLOT} INTEGER
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