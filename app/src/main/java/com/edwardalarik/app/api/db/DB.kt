package com.edwardalarik.app.api.db

class DB {
    companion object {
        const val DB_NAME = "poke_data_base.db"
        const val DB_VERSION = 1

        // Tablas
        const val TAB_POKEMON = "tab_pokemon"
        const val TAB_ABILITY = "tab_ability"
        const val TAB_TYPE = "tab_type"
        const val TAB_MOVE = "tab_move"

        // ids
        const val COL_ID_POKEMON = "col_id_pokemon"
        const val COL_ID_ABILITY = "col_id_ability"
        const val COL_ID_TYPE = "col_id_type"
        const val COL_ID_MOVE = "col_id_move"

        // columnas
        const val COL_ORDER = "col_order"
        const val COL_NAME = "col_name"
        const val COL_HEIGHT = "col_height"
        const val COL_WEIGHT = "col_weight"
        const val COL_BASE_EXPERIENCE = "col_base_experience"
        const val COL_IS_DEFAULT = "col_is_default"
        const val COL_TYPES = "col_types"
        const val COL_ABILITIES = "col_abilities"
        const val COL_MOVES = "col_moves"
        const val COL_LOCATION_AREA_ENCOUNTERS = "col_location_area_encounters"
    }
}