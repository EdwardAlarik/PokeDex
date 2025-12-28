package com.edwardalarik.app.api.db

class DB {
    companion object {
        const val DB_NAME = "poke_data_base.db"
        const val DB_VERSION = 1

        // Catalogos
        const val TAB_ABILITY = "tab_ability"
        const val TAB_TYPE = "tab_type"
        const val TAB_MOVE = "tab_move"

        // Tablas
        const val TAB_POKEMON = "tab_pokemon"
        const val TAB_POKEMON_ABILITIES = "tab_pokemon_abilities"
        const val TAB_POKEMON_STATS = "tab_pokemon_stats"
        const val TAB_POKEMON_TYPES = "tab_pokemon_types"

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
        const val COL_SPECIES = "col_species"
        const val COL_LOCATION_AREA_ENCOUNTERS = "col_location_area_encounters"
        const val COL_FAV = "col_fav"
        const val COL_DESCRIPTION = "col_description"
        const val COL_IS_HIDDEN = "col_is_hidden"
        const val COL_SLOT = "col_slot"
        const val COL_BASE_STAT = "col_base_stat"
        const val COL_EFFORT = "col_effort"
        const val COL_CRIES_LATEST = "col_cries_latest"
        const val COL_CRIES_LEGACY = "col_cries_legacy"
    }
}