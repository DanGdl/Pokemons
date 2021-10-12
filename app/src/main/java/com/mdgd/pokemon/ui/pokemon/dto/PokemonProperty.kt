package com.mdgd.pokemon.ui.pokemon.dto

interface PokemonProperty {
    val type: Int
    val nestingLevel: Int
        get() = 0

    companion object {
        const val PROPERTY_IMAGE = 2
        const val PROPERTY_LABEL = 3
        const val PROPERTY_TITLE = 4
        const val PROPERTY_TEXT = 5
    }
}
