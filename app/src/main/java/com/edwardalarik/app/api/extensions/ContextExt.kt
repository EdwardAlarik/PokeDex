package com.edwardalarik.app.api.extensions

import android.content.Context
import android.widget.ImageView
import com.edwardalarik.app.R

fun Context.typeColor(typeName: String): Int {
    return when (typeName.lowercase()) {
        "normal" -> getColor(R.color.normal)
        "fire" -> getColor(R.color.fire)
        "water" -> getColor(R.color.water)
        "electric" -> getColor(R.color.electric)
        "grass" -> getColor(R.color.grass)
        "ice" -> getColor(R.color.ice)
        "fighting" -> getColor(R.color.fighting)
        "poison" -> getColor(R.color.poison)
        "ground" -> getColor(R.color.ground)
        "flying" -> getColor(R.color.flying)
        "psychic" -> getColor(R.color.psychic)
        "bug" -> getColor(R.color.bug)
        "rock" -> getColor(R.color.rock)
        "ghost" -> getColor(R.color.ghost)
        "dragon" -> getColor(R.color.dragon)
        "dark" -> getColor(R.color.dark)
        "steel" -> getColor(R.color.steel)
        "fairy" -> getColor(R.color.fairy)
        else -> getColor(R.color.white)
    }
}

fun ImageView.setTypeIcon(typeName: String) {
    val icon = when (typeName.lowercase()) {
        "normal" -> R.drawable.type_normal
        "fire" -> R.drawable.type_fire
        "water" -> R.drawable.type_water
        "electric" -> R.drawable.type_electric
        "grass" -> R.drawable.type_grass
        "ice" -> R.drawable.type_ice
        "fighting" -> R.drawable.type_fighting
        "poison" -> R.drawable.type_poison
        "ground" -> R.drawable.type_ground
        "flying" -> R.drawable.type_flying
        "psychic" -> R.drawable.type_psychic
        "bug" -> R.drawable.type_bug
        "rock" -> R.drawable.type_rock
        "ghost" -> R.drawable.type_ghost
        "dragon" -> R.drawable.type_dragon
        "dark" -> R.drawable.type_dark
        "steel" -> R.drawable.type_steel
        "fairy" -> R.drawable.type_fairy
        else -> R.drawable.ic_question
    }

    setImageResource(icon)
}