package com.edwardalarik.app.api.extensions

import java.text.Normalizer
import java.util.regex.Pattern

fun String.convertirNumerosALetras(): String {
    val sb = StringBuilder()

    for (caracter in this) {
        when (caracter) {
            '0' -> sb.append('O')
            '1' -> sb.append('I')
            '2' -> sb.append('Z')
            '3' -> sb.append('E')
            '4' -> sb.append('A')
            '5' -> sb.append('S')
            '6' -> sb.append('G')
            '7' -> sb.append('T')
            '8' -> sb.append('B')
            '9' -> sb.append('P')
            else -> sb.append(caracter)
        }
    }

    return sb.toString()
}

fun String.splitName(): ArrayList<String> {
    val delim = " "

    var tokens = Pattern.compile(delim).split(trim())
    var names = ArrayList<String>()

    val special_tokens = ArrayList(
        mutableListOf(
            "DA", "DAS", "DE", "DEL", "DER", "DI", "DIE", "DD", "EL", "LA", "LOS", "LAS",
            "LE", "LES", "MAC", "MC", "VAN", "VON", "MA.", "MA",
            "A", "A.", "B", "B.", "C", "C.", "D", "D.", "E", "E.", "F", "F.",
            "G", "G.", "H", "H.", "I", "I.", "J", "J.", "K", "K.", "L", "L.",
            "M", "M.", "N", "N.", "O", "O.", "P", "P.", "Q", "Q.", "R", "R.",
            "S", "S.", "T", "T.", "U", "U.", "V", "V.", "W", "W.", "X", "X.",
            "Y", "Y.", "Z", "Z."
        )
    )

    var prev = ""
    var cont = 0

    for (i in tokens.indices) {
        if (special_tokens.contains(tokens[i])) {
            prev += tokens[i] + " "
        } else {
            names.add(prev + tokens[i])
            prev = ""
            cont++
        }
    }

    return names
}

fun String.removerAcentos(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
}

fun String.toCapital(): String = this.lowercase().replaceFirstChar { it.uppercase() }

fun String.toId(): Int {
    return this.trimEnd('/')
        .substringAfterLast("/")
        .toIntOrNull() ?: -1
}

fun Boolean.toTrueFalse(): Int {
    if (this) {
        return 1
    } else {
        return 0
    }
}

fun Int.toTrueFalse(): Boolean {
    if (this.equals(1)) {
        return true
    } else {
        return false
    }
}