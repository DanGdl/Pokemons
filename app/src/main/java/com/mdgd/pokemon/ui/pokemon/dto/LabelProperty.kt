package com.mdgd.pokemon.ui.pokemon.dto

class LabelProperty : TitleProperty {
    val text: String
    val titleStr: String

    constructor(labelResId: Int, text: String) : super(labelResId, 0) {
        this.text = text
        titleStr = ""
    }

    constructor(name: String, text: String, nestingLevel: Int) : super(0, nestingLevel) {
        titleStr = name
        this.text = text
    }

    override fun getViewHolderType(position: Int): Int = PokemonProperty.PROPERTY_LABEL
}
