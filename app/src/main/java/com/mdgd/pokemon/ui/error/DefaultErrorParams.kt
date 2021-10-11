package com.mdgd.pokemon.ui.error

data class DefaultErrorParams(
    override val isVisible: Boolean = false,
    override val title: String = "",
    override val message: String = ""
) : ErrorParams {

    override fun hide() = copy(isVisible = false)
}
