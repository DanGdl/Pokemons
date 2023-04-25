package com.mdgd.pokemon.ui.pokemon.dto

import com.mdgd.pokemon.adapter.ViewHolderDataItem

interface PokemonProperty : ViewHolderDataItem {
    val nestingLevel: Int
        get() = 0

    companion object {
        const val EMPTY_VIEW = 1
        const val PROPERTY_IMAGE = 2
        const val PROPERTY_LABEL = 3
        const val PROPERTY_TITLE = 4
        const val PROPERTY_TEXT = 5
    }
}
