package com.mdgd.pokemon.ui.pokemons

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.error.ErrorParams

data class PokemonsUiState(
    val isLoading: Boolean = false,

    val isAttackActive: Boolean = false,
    val isDefenceActive: Boolean = false,
    val isSpeedActive: Boolean = false,

    val pokemons: List<PokemonFullDataSchema> = listOf(),

    override val isVisible: Boolean = false,
    override val title: String = "",
    override val message: String = ""
) : ErrorParams {

    override fun hide() = copy(isVisible = false)
}