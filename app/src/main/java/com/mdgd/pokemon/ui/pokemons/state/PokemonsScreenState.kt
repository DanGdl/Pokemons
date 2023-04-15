package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.AbstractState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

open class PokemonsScreenState(
    val isProgressVisible: Boolean = false,
    val list: List<PokemonFullDataSchema> = listOf(),
    protected val availableFilters: List<String> = listOf(),
    val activeFilters: List<String> = listOf()
) : AbstractState<PokemonsContract.View, PokemonsScreenState>() {

    override fun visit(screen: PokemonsContract.View) {
        if (isProgressVisible) {
            screen.showProgress()
        } else {
            screen.hideProgress()
        }
        screen.setItems(list)
        for (filter in availableFilters) {
            screen.updateFilterButtons(activeFilters.contains(filter), filter)
        }
    }


    // PARTIAL STATES
    class Loading : PokemonsScreenState(true) {

        override fun merge(prevState: PokemonsScreenState): PokemonsScreenState {
            return PokemonsScreenState(
                isProgressVisible, prevState.list,
                prevState.availableFilters, prevState.activeFilters
            )
        }
    }

    class SetData(
        list: List<PokemonFullDataSchema>, availableFilters: List<String>
    ) : PokemonsScreenState(false, list, availableFilters) {

        override fun merge(prevState: PokemonsScreenState): PokemonsScreenState {
            return PokemonsScreenState(
                isProgressVisible, list, availableFilters, prevState.activeFilters
            )
        }
    }

    class AddData(
        list: List<PokemonFullDataSchema>
    ) : PokemonsScreenState(false, list) {

        override fun merge(prevState: PokemonsScreenState): PokemonsScreenState {
            val items = list.toMutableList()
            items.addAll(0, prevState.list)
            return PokemonsScreenState(
                isProgressVisible, items, prevState.availableFilters, prevState.activeFilters
            )
        }
    }

    class UpdateData(
        items: List<PokemonFullDataSchema>
    ) : PokemonsScreenState(false, items) {

        override fun merge(prevState: PokemonsScreenState): PokemonsScreenState {
            return PokemonsScreenState(
                isProgressVisible, list, prevState.availableFilters, prevState.activeFilters
            )
        }
    }

    class ChangeFilterState(filters: List<String>) : PokemonsScreenState(activeFilters = filters) {

        override fun merge(prevState: PokemonsScreenState): PokemonsScreenState {
            return PokemonsScreenState(
                isProgressVisible, list, prevState.availableFilters, activeFilters
            )
        }
    }

    // EOF: PARTIAL STATES
}
