package com.edwardalarik.app.api.webmodel

class listPokemon(
    val offset: Int = 0,
    val limit: Int = 1000
) {
    var count: Int = 0
    var next: String = ""
    var previous: String = ""
    var results: ArrayList<result> = ArrayList()

    class result (
        val name: String = "",
        val url: String = ""
    )
}