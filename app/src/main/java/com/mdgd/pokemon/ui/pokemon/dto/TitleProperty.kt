package com.mdgd.pokemon.ui.pokemon.dto

open class TitleProperty @JvmOverloads constructor(
    val titleResId: Int, override val nestingLevel: Int = 0
) : PokemonProperty {
    override fun getViewHolderType(position: Int): Int = PokemonProperty.PROPERTY_TITLE
}
