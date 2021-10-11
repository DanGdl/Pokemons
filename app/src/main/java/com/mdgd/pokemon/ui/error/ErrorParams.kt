package com.mdgd.pokemon.ui.error

interface ErrorParams {
    val title: String
    val message: String
    val isVisible: Boolean

    fun hide(): ErrorParams
}
