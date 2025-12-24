package com.edwardalarik.app.objects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkManager
import com.edwardalarik.app.Log
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.api.webmodel.listPokemon
import java.util.concurrent.ExecutorService

class ObjObserver(
    private val executorService: ExecutorService,
    private val workManager: WorkManager
) {
    init {
        Log.d(this::class.java.simpleName, "Proceso 'HILT', DEBUG: Se ha iniciado la clase ${this::class.java.simpleName}")
    }

    val _listPokemon = MutableLiveData(ArrayList<KModels.ListPokemon>())
    val listPokemon: LiveData<ArrayList<KModels.ListPokemon>> = _listPokemon

    val _listTypes = MutableLiveData(ArrayList<KModels.ListTypes>())
    val listTypes: LiveData<ArrayList<KModels.ListTypes>> = _listTypes

    val _dataPokemon = MutableLiveData<KModels.Pokemon>()
    val dataPokemon: LiveData<KModels.Pokemon> = _dataPokemon
}