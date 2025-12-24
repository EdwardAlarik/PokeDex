package com.edwardalarik.app.objects

import com.edwardalarik.app.Log
import com.edwardalarik.app.api.db.DB
import com.edwardalarik.app.api.extensions.toTrueFalse
import com.edwardalarik.app.api.logic.KQuerys
import com.edwardalarik.app.api.models.KModels
import com.edwardalarik.app.api.webmodel.listAbility
import com.edwardalarik.app.api.webmodel.listPokemon
import java.util.concurrent.ExecutorService
import kotlin.Int
import kotlin.String

class ObjPokemon(
    private val objVariable: ObjVariable,
    private val objBD: ObjDB,
    private val objObserver: ObjObserver,
    private val executorService: ExecutorService
) {
    init {
        Log.d(
            this::class.java.simpleName,
            "Proceso 'HILT', DEBUG: Se ha iniciado la clase ${this::class.java.simpleName}"
        )
    }

    fun getListPokemon() {
        executorService.execute {
            objObserver._listPokemon.value?.clear()
            objBD.db.rawQuery(KQuerys.queryListPokemon, null).use { c ->
                if (c.moveToFirst()) {
                    do {
                        objObserver._listPokemon.value?.add(
                            KModels.ListPokemon(
                                id = c.getInt(c.getColumnIndexOrThrow(DB.COL_ID_POKEMON)),
                                order = c.getInt(c.getColumnIndexOrThrow(DB.COL_ORDER)),
                                name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: "",
                                types = c.getString(c.getColumnIndexOrThrow(DB.COL_TYPES)) ?: "",
                            )
                        )
                    } while (c.moveToNext())
                } else {
                    objObserver._listPokemon.value?.add(
                        KModels.ListPokemon(
                            id = 0,
                            order = 0,
                            name = "",
                            types = ""
                        )
                    )
                }
                objObserver._listPokemon.postValue(objObserver._listPokemon.value)
            }
        }
    }

    fun getListType() {
        executorService.execute {
            objObserver._listTypes.value?.clear()
            objBD.db.rawQuery(KQuerys.queryListType, null).use { c ->
                if (c.moveToFirst()) {
                    do {
                        objObserver._listTypes.value?.add(
                            KModels.ListTypes(
                                id = c.getInt(c.getColumnIndexOrThrow(DB.COL_ID_TYPE)),
                                name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: "",
                            )
                        )
                    } while (c.moveToNext())
                } else {
                    objObserver._listTypes.value?.add(
                        KModels.ListTypes(
                            id = 0,
                            name = ""
                        )
                    )
                }
                objObserver._listTypes.postValue(objObserver._listTypes.value)
            }
        }
    }

    fun getPokemon(idPokemon: Int) {
        executorService.execute {
            objBD.db.rawQuery(KQuerys.queryPokemon(idPokemon), null).use { c ->
                if (c.moveToFirst()) {
                    objObserver._dataPokemon.postValue(
                        KModels.Pokemon(
                            abilities = c.getString(c.getColumnIndexOrThrow(DB.COL_ABILITIES)) ?: "",
                            base_experience = c.getInt(c.getColumnIndexOrThrow(DB.COL_BASE_EXPERIENCE)),
                            cries = "",
                            forms = "",
                            game_indices = "",
                            height = c.getInt(c.getColumnIndexOrThrow(DB.COL_HEIGHT)),
                            held_items = "",
                            id = c.getInt(c.getColumnIndexOrThrow(DB.COL_ID_POKEMON)),
                            is_default = c.getInt(c.getColumnIndexOrThrow(DB.COL_IS_DEFAULT)).toTrueFalse(),
                            location_area_encounters = c.getString(c.getColumnIndexOrThrow(DB.COL_LOCATION_AREA_ENCOUNTERS)),
                            moves = c.getString(c.getColumnIndexOrThrow(DB.COL_MOVES)) ?: "",
                            name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)),
                            order = c.getInt(c.getColumnIndexOrThrow(DB.COL_ORDER)),
                            past_abilities = "",
                            past_types = "",
                            species = "",
                            sprites = "",
                            stats = "",
                            types = c.getString(c.getColumnIndexOrThrow(DB.COL_TYPES)) ?: "",
                            weight = c.getInt(c.getColumnIndexOrThrow(DB.COL_WEIGHT))
                        )
                    )
                } else {
                    objObserver._dataPokemon.postValue(KModels.Pokemon())
                }
            }
        }
    }

    fun getAbility(idAbility: Int): String {
        objBD.db.rawQuery(KQuerys.queryAbility(idAbility), null).use { c ->
            if (c.moveToFirst()) {
                return c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: ""
            } else {
                return ""
            }
        }
    }

    fun getType(idType: Int): String {
        objBD.db.rawQuery(KQuerys.queryType(idType), null).use { c ->
            if (c.moveToFirst()) {
                return c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: ""
            } else {
                return ""
            }
        }
    }
}