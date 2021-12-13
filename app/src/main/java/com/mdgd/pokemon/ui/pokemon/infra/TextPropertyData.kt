package com.mdgd.pokemon.ui.pokemon.infra

class TextPropertyData(override val text: String, override val nestingLevel: Int) : TextProperty {
    override val type: Int
        get() = PokemonProperty.PROPERTY_TEXT
}
