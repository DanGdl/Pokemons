package com.mdgd.pokemon.ui.pokemon.dto

open class TitlePropertyData @JvmOverloads constructor(override val titleResId: Int, override val nestingLevel: Int = 0) : TitleProperty {
    override val type: Int
        get() = PokemonProperty.PROPERTY_TITLE
}
