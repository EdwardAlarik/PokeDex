package com.edwardalarik.app.api.webmodel

class EvolutionChainResponse {
    var baby_trigger_item: String = ""
    var chain: Chain = Chain(
        evolution_details = ArrayList(),
        evolves_to = ArrayList(),
        is_baby = false,
        species = Result()
    )
    var id: Int = 0

    class Result (
        val name: String = "",
        val url: String = ""
    )

    class Chain (
        val evolution_details: ArrayList<EvolutionDetail> = ArrayList(),
        val evolves_to: ArrayList<Chain> = ArrayList(),
        val is_baby: Boolean = false,
        val species: Result
    )

    class EvolutionDetail (
        val base_form_id: Int = 0,
        val gender: Int = 0,
        val held_item: Any?,
        val item: Any?,
        val known_move: Any?,
        val known_move_type: Any?,
        val location: Any?,
        val min_affection: Int = 0,
        val min_beauty: Int = 0,
        val min_happiness: Int = 0,
        val min_level: Int = 0,
        val needs_overworld_rain: Boolean = false,
        val party_species: Any?,
        val party_type: Any?,
        val region_id: Int = 0,
        val relative_physical_stats: Int = 0,
        val time_of_day: String = "",
        val trade_species: Any?,
        val trigger: Result,
        val turn_upside_down: Boolean = false
    )
}