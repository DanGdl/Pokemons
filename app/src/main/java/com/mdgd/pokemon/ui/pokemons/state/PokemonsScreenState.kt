package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

sealed class PokemonsScreenState(protected val list: MutableList<PokemonFullDataSchema>,
                                 protected val availableFilters: List<String>,
                                 protected val activeFilters: MutableList<String>) : ScreenState<PokemonsContract.View> {

    fun getItems(): List<PokemonFullDataSchema> {
        return ArrayList(list)
    }

    override fun visit(screen: PokemonsContract.View) {
        screen.setItems(list)
        for (filter in availableFilters) {
            screen.updateFilterButtons(activeFilters.contains(filter), filter)
        }
    }

    @JvmName("getActiveFilters1")
    fun getActiveFilters(): List<String> {
        return ArrayList(activeFilters)
    }


    class Initial(availableFilters: List<String>)
        : PokemonsScreenState(ArrayList(0), availableFilters, ArrayList(0))

    class SetData(list: List<PokemonFullDataSchema>, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(list), lastState.availableFilters, ArrayList(lastState.activeFilters)) {
    }

    class AddData(list: List<PokemonFullDataSchema>, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(lastState.list), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        init {
            super.list.addAll(list)
        }
    }

    class UpdateData(items: List<PokemonFullDataSchema>, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(items), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.updateItems(list)
        }
    }

    class ChangeFilterState(filter: String, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(lastState.list), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        init {
            if (activeFilters.contains(filter)) {
                activeFilters.remove(filter)
            } else {
                activeFilters.add(filter)
            }
        }

        override fun visit(screen: PokemonsContract.View) {
            super.visit(screen)
            screen.hideProgress()
        }
    }
}
