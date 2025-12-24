package com.edwardalarik.app.api.logic

import com.edwardalarik.app.api.db.DB
import com.edwardalarik.app.api.webmodel.listAbility

class KQuerys {
    companion object {
        const val queryListPokemon = "SELECT * FROM ${DB.TAB_POKEMON} ORDER BY ${DB.COL_ID_POKEMON} ASC;"
        const val queryListType = "SELECT * FROM ${DB.TAB_TYPE} ORDER BY ${DB.COL_NAME} ASC;"
        fun queryPokemon(idPokemon: Int) = "SELECT * FROM ${DB.TAB_POKEMON} WHERE ${DB.COL_ID_POKEMON} = $idPokemon;"
        fun queryAbility(idAbility: Int) = "SELECT * FROM ${DB.TAB_ABILITY} WHERE ${DB.COL_ID_ABILITY} = $idAbility;"
        fun queryType(idType: Int) = "SELECT * FROM ${DB.TAB_TYPE} WHERE ${DB.COL_ID_TYPE} = $idType;"
    }
}