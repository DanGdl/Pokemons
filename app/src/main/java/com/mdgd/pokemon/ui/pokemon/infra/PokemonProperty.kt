package com.mdgd.pokemon.ui.pokemon.infra

interface PokemonProperty {
    val type: Int
    val nestingLevel: Int
        get() = 0

    companion object {
        const val PROPERTY_IMAGE = 1
        const val PROPERTY_LABEL = 2
        const val PROPERTY_TITLE = 3
        const val PROPERTY_TEXT = 4
    }
}
