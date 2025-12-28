package com.edwardalarik.app.objects

import com.edwardalarik.app.Log
import com.edwardalarik.app.api.db.DB
import com.edwardalarik.app.api.logic.KQuerys

class ObjVariable(
    private val objDB: ObjDB
) {
    init {
        Log.d(
            this::class.java.simpleName,
            "Proceso 'HILT', DEBUG: Se ha iniciado la clase ${this::class.java.simpleName}"
        )
    }

    var idPokemon: Int = 0
    var q: String = ""

    fun existPokemon(idPokemon: Int): Boolean {
        var isExiste = false
        objDB.db.rawQuery(KQuerys.queryPokemon(idPokemon), null).use { c ->
            if (c.moveToFirst()) {
                isExiste = true
            } else {
                isExiste = false
            }
        }
        return isExiste
    }

    fun existDataPokemon(idPokemon: Int): Boolean {
        var isExiste = false
        objDB.db.rawQuery(KQuerys.queryPokemon(idPokemon), null).use { c ->
            if (c.moveToFirst()) {
                isExiste =
                    if (c.getString(c.getColumnIndexOrThrow(DB.COL_ORDER)).isNullOrEmpty()) {
                        false
                    } else {
                        true
                    }
            }
        }
        return isExiste
    }
}