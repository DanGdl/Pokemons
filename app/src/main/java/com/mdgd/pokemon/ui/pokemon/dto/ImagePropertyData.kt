package com.mdgd.pokemon.ui.pokemon.dto

class ImagePropertyData(override val imageUrl: String) : ImageProperty {
    override val type: Int
        get() = PokemonProperty.Companion.PROPERTY_IMAGE
}
