package com.mdgd.pokemon.ui.pokemon.infra

class LabelPropertyData : TitlePropertyData, LabelProperty {
    override val text: String
    override val titleStr: String

    constructor(labelResId: Int, text: String) : super(labelResId, 0) {
        this.text = text
        titleStr = ""
    }

    constructor(name: String, text: String, nestingLevel: Int) : super(0, nestingLevel) {
        titleStr = name
        this.text = text
    }

    override val type: Int
        get() = PokemonProperty.Companion.PROPERTY_LABEL
}
