package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

sealed class PokemonsScreenState(
        private val isProgressVisible: Boolean = false,
        protected val list: MutableList<PokemonFullDataSchema> = mutableListOf(),
        protected val availableFilters: MutableList<String> = mutableListOf(),
        protected val activeFilters: MutableList<String> = mutableListOf())
    : ScreenState<PokemonsContract.View, PokemonsScreenState> {

    fun getItems(): List<PokemonFullDataSchema> {
        return ArrayList(list)
    }

    @JvmName("getActiveFilters1")
    fun getActiveFilters(): List<String> {
        return ArrayList(activeFilters)
    }

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


    class Loading() : PokemonsScreenState(true) {

        override fun merge(prevState: PokemonsScreenState) {
            list.addAll(prevState.list)
            availableFilters.addAll(prevState.availableFilters)
            activeFilters.addAll(prevState.activeFilters)
        }
    }

    class SetData(list: List<PokemonFullDataSchema>, availableFilters: List<String>)
        : PokemonsScreenState(false, ArrayList(list), ArrayList(availableFilters)) {

        override fun merge(prevState: PokemonsScreenState) {
            activeFilters.addAll(prevState.activeFilters)
        }
    }

    class AddData(list: List<PokemonFullDataSchema>) : PokemonsScreenState(false, ArrayList(list)) {

        override fun merge(prevState: PokemonsScreenState) {
            list.addAll(0, prevState.list)
            availableFilters.addAll(prevState.availableFilters)
            activeFilters.addAll(prevState.activeFilters)
        }
    }

    class UpdateData(items: List<PokemonFullDataSchema>)
        : PokemonsScreenState(false, ArrayList(items)) {

        override fun merge(prevState: PokemonsScreenState) {
            availableFilters.addAll(prevState.availableFilters)
            activeFilters.addAll(prevState.activeFilters)
        }

        override fun visit(screen: PokemonsContract.View) {
            super.visit(screen)
            screen.scrollToStart()
        }
    }

    class ChangeFilterState(private val filter: String) : PokemonsScreenState() {

        override fun merge(prevState: PokemonsScreenState) {
            list.addAll(prevState.list)
            availableFilters.addAll(prevState.availableFilters)
            activeFilters.addAll(prevState.activeFilters)

            if (activeFilters.contains(filter)) {
                activeFilters.remove(filter)
            } else {
                activeFilters.add(filter)
            }
        }
    }
}
