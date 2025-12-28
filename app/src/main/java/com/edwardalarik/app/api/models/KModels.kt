package com.edwardalarik.app.api.models

class KModels {
    data class ListPokemon(
        var id: Int = 0,
        var order: Int = 0,
        var name: String = "",
        var types: String = ""
    )

    data class ListAbilitys(
        var id: Int = 0,
        var name: String = ""
    )

    data class ListTypes(
        var id: Int = 0,
        var name: String = ""
    )

    data class ListStats(
        var name: String = "",
        var base_stat: Int = 0,
        var type: String = ""
    )

    data class Pokemon(
        var abilities: String = "",
        var base_experience: Int = 0,
        var cries_latest: String = "",
        var cries_legacy: String = "",
        var forms: String = "",
        var game_indices: String = "",
        var height: Int = 0,
        var held_items: String = "",
        var id: Int = 0,
        var is_default: Boolean = false,
        var location_area_encounters: String = "",
        var moves: String = "",
        var name: String = "",
        var order: Int = 0,
        var past_abilities: String = "",
        var past_types: String = "",
        var species: String = "",
        var sprites: String = "",
        var stats: String = "",
        var types: String = "",
        var weight: Int = 0,
        var fav: Int = 0,
        var description: String = ""
    )
}