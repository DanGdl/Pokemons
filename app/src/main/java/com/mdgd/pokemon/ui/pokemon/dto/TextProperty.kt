package com.mdgd.pokemon.ui.pokemon.dto

class TextProperty(val text: String, override val nestingLevel: Int) : PokemonProperty {
    override fun getViewHolderType(position: Int): Int = PokemonProperty.PROPERTY_TEXT
}
