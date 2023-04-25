package com.mdgd.pokemon.ui.pokemon.dto

class ImageProperty(val imageUrl: String) : PokemonProperty {
    override fun getViewHolderType(position: Int): Int = PokemonProperty.PROPERTY_IMAGE
}
