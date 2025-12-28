package com.edwardalarik.app.api.webmodel

class PokemonSpecies {
    var base_happiness: Int = 0
    var capture_rate: Int = 0
    var color: Result = Result()
    var egg_groups: ArrayList<Result> = ArrayList()
    var evolution_chain: ResultWeb = ResultWeb()
    var evolves_from_species: Result = Result()
    var flavor_text_entries: ArrayList<FlavorTextEntries> = ArrayList()
    var form_descriptions: Any? = null
    var forms_switchable: Boolean = false
    var gender_rate: Int = 0
    var genera: ArrayList<Genera> = ArrayList()
    var generation: Result = Result()
    var growth_rate: Result = Result()
    var habitat: Result = Result()
    var has_gender_differences: Boolean = false
    var hatch_counter: Int = 0
    var id: Int = 0
    var is_baby: Boolean = false
    var is_legendary: Boolean = false
    var is_mythical: Boolean = false
    var name: String = ""
    var names: ArrayList<Names> = ArrayList()
    var order: Int = 0
    var pal_park_encounters: ArrayList<PalParkEncounters> = ArrayList()
    var pokedex_numbers: ArrayList<PokedexNumbers> = ArrayList()
    var shape: Result = Result()
    var varieties: ArrayList<Varieties> = ArrayList()

    class Result (
        val name: String = "",
        val url: String = ""
    )

    class ResultWeb (
        val url: String = ""
    )

    class FlavorTextEntries (
        val flavor_text: String = "",
        val language: Result,
        val version: Result
    )

    class Genera (
        val genus: String = "",
        val language: Result
    )

    class Names (
        val language: Result,
        val name: String = ""
    )

    class PalParkEncounters (
        val area: Result,
        val base_score: Int = 0,
        val rate: Int = 0
    )

    class PokedexNumbers (
        val entry_number: Int = 0,
        val pokedex: Result
    )

    class Varieties (
        val is_default: Boolean = false,
        val pokemon: Result
    )
}