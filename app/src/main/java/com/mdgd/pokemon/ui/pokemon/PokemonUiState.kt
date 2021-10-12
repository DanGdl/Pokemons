package com.mdgd.pokemon.ui.pokemon

import com.mdgd.pokemon.ui.error.ErrorParams
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty

data class PokemonUiState(
    val properties: List<PokemonProperty> = listOf(),

    override val title: String = "",
    override val message: String = "",
    override val isVisible: Boolean = false
) : ErrorParams {

    override fun hide() = copy(isVisible = false)
}
