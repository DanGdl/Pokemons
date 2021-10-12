package com.mdgd.pokemon.ui.pokemon.dto

class TextPropertyData(override val text: String, override val nestingLevel: Int) : TextProperty {
    override val type: Int
        get() = PokemonProperty.Companion.PROPERTY_TEXT
}
