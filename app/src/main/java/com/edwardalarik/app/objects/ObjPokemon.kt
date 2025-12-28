package com.edwardalarik.app.objects

import com.edwardalarik.app.Log
import com.edwardalarik.app.api.db.DB
import com.edwardalarik.app.api.extensions.toTrueFalse
import com.edwardalarik.app.api.logic.KQuerys
import com.edwardalarik.app.api.models.KModels
import java.util.concurrent.ExecutorService

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
                                types = "fire",
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

    fun getListPokemonSearch(q: String) {
        executorService.execute {
            objObserver._listPokemon.value?.clear()
            objBD.db.rawQuery(KQuerys.queryListPokemonSearch(q = q), null).use { c ->
                if (c.moveToFirst()) {
                    do {
                        objObserver._listPokemon.value?.add(
                            KModels.ListPokemon(
                                id = c.getInt(c.getColumnIndexOrThrow(DB.COL_ID_POKEMON)),
                                order = c.getInt(c.getColumnIndexOrThrow(DB.COL_ORDER)),
                                name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: "",
                                types = "fire",
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
                            abilities = "",
                            base_experience = c.getInt(c.getColumnIndexOrThrow(DB.COL_BASE_EXPERIENCE)),
                            cries_latest = c.getString(c.getColumnIndexOrThrow(DB.COL_CRIES_LATEST)) ?: "",
                            cries_legacy = c.getString(c.getColumnIndexOrThrow(DB.COL_CRIES_LEGACY)) ?: "",
                            forms = "",
                            game_indices = "",
                            height = c.getInt(c.getColumnIndexOrThrow(DB.COL_HEIGHT)),
                            held_items = "",
                            id = c.getInt(c.getColumnIndexOrThrow(DB.COL_ID_POKEMON)),
                            is_default = c.getInt(c.getColumnIndexOrThrow(DB.COL_IS_DEFAULT)).toTrueFalse(),
                            location_area_encounters = c.getString(c.getColumnIndexOrThrow(DB.COL_LOCATION_AREA_ENCOUNTERS)),
                            moves = "",
                            name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)),
                            order = c.getInt(c.getColumnIndexOrThrow(DB.COL_ORDER)),
                            past_abilities = "",
                            past_types = "",
                            species = c.getString(c.getColumnIndexOrThrow(DB.COL_SPECIES)) ?: "",
                            sprites = "",
                            stats = "",
                            types = c.getString(c.getColumnIndexOrThrow("typePokemon")) ?: "",
                            weight = c.getInt(c.getColumnIndexOrThrow(DB.COL_WEIGHT)),
                            fav = c.getInt(c.getColumnIndexOrThrow(DB.COL_FAV)),
                            description = c.getString(c.getColumnIndexOrThrow(DB.COL_DESCRIPTION)) ?: ""
                        )
                    )
                } else {
                    objObserver._dataPokemon.postValue(KModels.Pokemon())
                }
            }
        }
    }

    fun getAbilysPokemon(idPokemon: Int) {
        executorService.execute {
            objObserver._dataAbilitysPokemon.value?.clear()
            objBD.db.rawQuery(KQuerys.queryTypesPokemon(idPokemon), null).use { c ->
                if (c.moveToFirst()) {
                    do {
                        objObserver._dataAbilitysPokemon.value?.add(
                            KModels.ListAbilitys(
                                id = 0,
                                name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: "",
                            )
                        )
                    } while (c.moveToNext())
                } else {
                    objObserver._dataAbilitysPokemon.value?.add(KModels.ListAbilitys())
                }
                objObserver._dataAbilitysPokemon.postValue(objObserver._dataAbilitysPokemon.value)
            }
        }
    }

    fun getTypesPokemon(idPokemon: Int) {
        executorService.execute {
            objObserver._dataTypesPokemon.value?.clear()
            objBD.db.rawQuery(KQuerys.queryTypesPokemon(idPokemon), null).use { c ->
                if (c.moveToFirst()) {
                    do {
                        objObserver._dataTypesPokemon.value?.add(
                            KModels.ListTypes(
                                id = 0,
                                name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: "",
                            )
                        )
                    } while (c.moveToNext())
                } else {
                    objObserver._dataTypesPokemon.value?.add(KModels.ListTypes())
                }
                objObserver._dataTypesPokemon.postValue(objObserver._dataTypesPokemon.value)
            }
        }
    }

    fun getStatsPokemon(idPokemon: Int) {
        executorService.execute {
            objObserver._dataStatsPokemon.value?.clear()
            objBD.db.rawQuery(KQuerys.queryStatPokemon(idPokemon), null).use { c ->
                if (c.moveToFirst()) {
                    do {
                        objObserver._dataStatsPokemon.value?.add(
                            KModels.ListStats(
                                name = c.getString(c.getColumnIndexOrThrow(DB.COL_NAME)) ?: "",
                                base_stat = c.getInt(c.getColumnIndexOrThrow(DB.COL_BASE_STAT)),
                                type = c.getString(c.getColumnIndexOrThrow("typePokemon")) ?: ""
                            )
                        )
                    } while (c.moveToNext())
                } else {
                    objObserver._dataStatsPokemon.value?.add(KModels.ListStats())
                }
                objObserver._dataStatsPokemon.postValue(objObserver._dataStatsPokemon.value)
            }
        }
    }

    fun checkFav(idPokemon: Int, favorite: Int) {
        if (favorite == 1) {
            objBD.frameDB.update(
                DB.TAB_POKEMON, listOf(
                    Pair(DB.COL_FAV, 0)
                ), "${DB.COL_ID_POKEMON} = '$idPokemon'"
            )
        } else {
            objBD.frameDB.update(
                DB.TAB_POKEMON, listOf(
                    Pair(DB.COL_FAV, 1)
                ), "${DB.COL_ID_POKEMON} = '$idPokemon'"
            )
        }
    }
}