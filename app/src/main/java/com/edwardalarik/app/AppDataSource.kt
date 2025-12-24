package com.edwardalarik.app

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.speech.SpeechRecognizer
import androidx.work.WorkManager
import com.edwardalarik.app.objects.ObjDB
import com.edwardalarik.app.objects.ObjNetwork
import com.edwardalarik.app.objects.ObjObserver
import com.edwardalarik.app.objects.ObjPokemon
import com.edwardalarik.app.objects.ObjVariable
import java.util.concurrent.ExecutorService

class AppDataSource(
    val application: Application,
    val mainThreadHandler: Handler,
    val executorService: ExecutorService,
    val db: SQLiteDatabase
) {
    init {
        Log.d(
            this::class.java.simpleName,
            "Proceso 'HILT', DEBUG: Se ha iniciado la clase ${this::class.java.simpleName}"
        )
    }

    val objDB by lazy { ObjDB(application, db, executorService) }
    val objObserver by lazy { ObjObserver(executorService, workManager) }
    val objVariable by lazy { ObjVariable(objDB) }
    val objNetwork by lazy { ObjNetwork(application, executorService, mainThreadHandler, objVariable, objDB, objObserver) }


    val objPokemon by lazy { ObjPokemon( objVariable, objDB, objObserver, executorService) }

    val workManager by lazy { WorkManager.getInstance(application) }
    val speechRecognizer: SpeechRecognizer by lazy { SpeechRecognizer.createSpeechRecognizer(application) }
}