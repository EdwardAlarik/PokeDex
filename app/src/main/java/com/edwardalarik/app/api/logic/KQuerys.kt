package com.edwardalarik.app.api.logic

import com.edwardalarik.app.api.db.DB

class KQuerys {
    companion object {
        const val queryListPokemon = "SELECT * FROM ${DB.TAB_POKEMON} ORDER BY ${DB.COL_ID_POKEMON} ASC;"
        const val queryListType = "SELECT * FROM ${DB.TAB_TYPE} ORDER BY ${DB.COL_NAME} ASC;"
        fun queryListPokemonSearch(q: String) = "SELECT * FROM ${DB.TAB_POKEMON} WHERE ${DB.COL_NAME} LIKE '%$q%' ORDER BY ${DB.COL_ID_POKEMON} ASC;"
        fun queryPokemon(idPokemon: Int) = "SELECT *, (SELECT ${DB.COL_NAME} FROM ${DB.TAB_POKEMON_TYPES} WHERE ${DB.COL_ID_POKEMON} = $idPokemon AND ${DB.COL_SLOT} = 1) AS typePokemon FROM ${DB.TAB_POKEMON} WHERE ${DB.COL_ID_POKEMON} = $idPokemon;"
        fun queryTypesPokemon(idPokemon: Int) = "SELECT * FROM ${DB.TAB_POKEMON_TYPES} WHERE ${DB.COL_ID_POKEMON} = $idPokemon ORDER BY ${DB.COL_SLOT} ASC;"
        fun queryStatPokemon(idPokemon: Int) = "SELECT *, (SELECT ${DB.COL_NAME} FROM ${DB.TAB_POKEMON_TYPES} WHERE ${DB.COL_ID_POKEMON} = $idPokemon AND ${DB.COL_SLOT} = 1) AS typePokemon FROM ${DB.TAB_POKEMON_STATS} WHERE ${DB.COL_ID_POKEMON} = $idPokemon;"





        fun queryAbility(idAbility: Int) = "SELECT * FROM ${DB.TAB_ABILITY} WHERE ${DB.COL_ID_ABILITY} = $idAbility;"
        fun queryType(idType: Int) = "SELECT * FROM ${DB.TAB_TYPE} WHERE ${DB.COL_ID_TYPE} = $idType;"
    }
}