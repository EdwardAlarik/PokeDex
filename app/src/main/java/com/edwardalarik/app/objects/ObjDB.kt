package com.edwardalarik.app.objects

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.edwardalarik.app.Log
import com.edwardalarik.app.api.db.DBFramework
import java.util.concurrent.ExecutorService

class ObjDB(
    val application: Application,
    val db: SQLiteDatabase,
    private val executorService: ExecutorService
) {
    init {
        Log.d(
            this::class.java.simpleName,
            "Proceso 'HILT', DEBUG: Se ha iniciado la clase ${this::class.java.simpleName}"
        )
    }

    val frameDB: DBFramework by lazy { DBFramework(db, executorService) }
}