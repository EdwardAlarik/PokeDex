package com.edwardalarik.app.api.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import com.edwardalarik.app.Log
import java.util.concurrent.ExecutorService

class DBFramework(
    val db: SQLiteDatabase,
    val executorService: ExecutorService
) {
    fun insert(
        tabla: String,
        tuplas: List<Pair<String, *>>,
        replace: Boolean = false,
        enableThread: Boolean = false
    ) {
        if (enableThread) {
            executorService.execute { insertFinal(tabla, tuplas, replace) }
        } else {
            insertFinal(tabla, tuplas, replace)
        }
    }

    private fun insertFinal(tabla: String, tuplas: List<Pair<String, *>>, replace: Boolean) {
        val init = (if (replace) "REPLACE" else "INSERT") + " INTO $tabla ("
        val builder = StringBuilder(init)
        for (c in tuplas) {
            builder.append("${c.first},")
        }
        builder.append(") VALUES (")
        for (v in tuplas) {
            builder.append("?,")
        }
        builder.append(");")
        val query = builder.toString().replace(",)", ")")
        Log.d(this::class.java.simpleName, query)
        db.compileStatement(query).use { sqLiteStatement ->
            tuplas.forEachIndexed { index, v ->
                when (v.second) {
                    is Int -> sqLiteStatement.bindString(index + 1, v.second.toString())
                    is String -> sqLiteStatement.bindString(index + 1, v.second as String)
                    is Double -> sqLiteStatement.bindDouble(index + 1, v.second as Double)
                    is Long -> sqLiteStatement.bindLong(index + 1, v.second as Long)
                    is ByteArray -> sqLiteStatement.bindBlob(index + 1, v.second as ByteArray)
                    else -> sqLiteStatement.bindNull(index + 1)
                }
            }
            try {
                sqLiteStatement.execute()
                Log.d(
                    this::class.java.simpleName,
                    "Proceso 'UPDATE', DEBUG: Query es $sqLiteStatement"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update(
        tabla: String,
        tuplas: List<Pair<String, *>>,
        where: String = "",
        enableThread: Boolean = false,
        allowInsert: Boolean = false
    ) {
        if (enableThread) {
            executorService.execute { updateFinal(tabla, tuplas, where, allowInsert) }
        } else {
            updateFinal(tabla, tuplas, where, allowInsert)
        }
    }

    private fun updateFinal(
        tabla: String, tuplas: List<Pair<String, *>>, where: String, allowInsert: Boolean
    ): Int {
        var res = 0
        compileStaticStatementUpdate(tabla, tuplas, where).use { sqLiteStatement ->
            tuplas.forEachIndexed { index, v ->
                when (v.second) {
                    is Int -> sqLiteStatement.bindString(index + 1, v.second.toString())
                    is Char -> sqLiteStatement.bindString(index + 1, (v.second as Char).toString())
                    is String -> sqLiteStatement.bindString(index + 1, v.second as String)
                    is Double -> sqLiteStatement.bindDouble(index + 1, v.second as Double)
                    is Long -> sqLiteStatement.bindLong(index + 1, v.second as Long)
                    is ByteArray -> sqLiteStatement.bindBlob(index + 1, v.second as ByteArray)
                    else -> sqLiteStatement.bindNull(index + 1)
                }
            }
            try {
                res = sqLiteStatement.executeUpdateDelete()
                Log.d(
                    this::class.java.simpleName,
                    "Proceso 'UPDATE', DEBUG: Query es $sqLiteStatement\nResultado es: $res"
                )
                if (res == 0 && allowInsert) {
                    insert(tabla, tuplas)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return res
    }

    private fun compileStaticStatementUpdate(
        tabla: String,
        tuplas: List<Pair<String, *>>,
        where: String,
    ): SQLiteStatement {
        val builder = StringBuilder()
        builder.append("UPDATE ")
        builder.append(tabla)
        builder.append(" SET ")
        for (c in tuplas) {
            builder.append("${c.first}=?,")
        }
        if (builder.endsWith(',')) builder.deleteCharAt(builder.length - 1)
        if (where.isNotEmpty()) {
            builder.append(" WHERE ")
            builder.append(where)
        }
        val query = builder.toString()

        Log.d(this::class.java.simpleName, "Proceso 'UPDATE', DEBUG: Ejecuta el query: $query")

        return db.compileStatement(query)
    }
}