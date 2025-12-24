package com.edwardalarik.app.api.webmodel

import com.google.gson.annotations.SerializedName

class Pokemon(
    var abilities: ArrayList<Abilities> = ArrayList(),
    var base_experience: Int = 0,
    var cries: Cries,
    var forms: ArrayList<Result> = ArrayList(),
    var game_indices: ArrayList<GameIndices> = ArrayList(),
    var height: Int = 0,
    var held_items: ArrayList<HeldItem> = ArrayList(),
    var id: Int = 0,
    var is_default: Boolean = false,
    var location_area_encounters: String = "",
    var moves: ArrayList<Moves> = ArrayList(),
    var name: String = "",
    var order: Int = 0,
    var past_abilities: ArrayList<PastAbilities> = ArrayList(),
    var past_types: ArrayList<PastType> = ArrayList(),
    var species: Result,
    var sprites: Sprites,
    var stats: ArrayList<Stats> = ArrayList(),
    var types: ArrayList<Types> = ArrayList(),
    var weight: Int = 0
) {
    class Result (
        val name: String = "",
        val url: String = ""
    )

    class Abilities (
        val ability: Result,
        val is_hidden: Boolean = false,
        val slot: Int = 0
    )

    class Cries (
        val latest: String = "",
        val legacy: String = ""
    )

    class GameIndices (
        val game_index: Int = 0,
        val version: Result
    )

    data class HeldItem(
        val item: Result,
        val version_details: List<HeldItemVersionDetail>
    )

    data class HeldItemVersionDetail(
        val rarity: Int,
        val version: Result
    )

    class Moves (
        val move: Result,
        val version_group_details: ArrayList<VersionGroupDetails> = ArrayList()
    )

    class VersionGroupDetails(
        val level_learned_at: Int = 0,
        val move_learn_method: Result,
        val order: String = "",
        val version_group: Result
    )

    class PastAbilities (
        val abilities: ArrayList<Abilities> = ArrayList(),
        val generation: Result
    )

    data class PastType(
        val generation: Result,
        val types: List<TypeSlot>
    )

    data class TypeSlot(
        val slot: Int,
        val type: Result
    )

    class Sprites (
        val back_default: String = "",
        val back_female: String = "",
        val back_shiny: String = "",
        val back_shiny_female: String = "",
        val front_default: String = "",
        val front_female: String = "",
        val front_shiny: String = "",
        val front_shiny_female: String = "",
        val other: Other
    )

    class Other (
        val dream_world: dreamWorld,
        val home: Home,
        @SerializedName("official-artwork")
        val officialArtwork: officialArtwork,
        val showdown: Showdown
    )

    class dreamWorld (
        val front_default: String = "",
        val front_female: String = ""
    )

    class Home (
        val front_default: String = "",
        val front_female: String = "",
        val front_shiny: String = "",
        val front_shiny_female: String = ""
    )

    class officialArtwork (
        val front_default: String = "",
        val front_shiny: String = ""
    )

    class Showdown  (
        val back_default: String = "",
        val back_female: String = "",
        val back_shiny: String = "",
        val back_shiny_female: String = "",
        val front_default: String = "",
        val front_female: String = "",
        val front_shiny: String = "",
        val front_shiny_female: String = ""
    )

    class Stats (
        val base_stat: String = "",
        val effort: String = "",
        val star: Result
    )

    class Types (
        val slot: Int,
        val type: Result
    )
}