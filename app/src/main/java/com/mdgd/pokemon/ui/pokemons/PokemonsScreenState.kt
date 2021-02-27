package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.ScreenState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

sealed class PokemonsScreenState(protected val list: MutableList<PokemonFullDataSchema>,
                                 protected val availableFilters: List<String>,
                                 protected val activeFilters: MutableList<String>) : ScreenState<PokemonsContract.View> {
    protected var isHandled = false

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

    class Loading(lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(lastState.list), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        override fun visit(screen: PokemonsContract.View) {
            super.visit(screen)
            if (!isHandled) {
                screen.showProgress()
                isHandled = true
            }
        }
    }

    class SetData(list: List<PokemonFullDataSchema>, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(list), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            super.visit(screen)
        }
    }

    class AddData(list: List<PokemonFullDataSchema>, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(lastState.list), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        init {
            super.list.addAll(list)
        }

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            super.visit(screen)
        }
    }

    class UpdateData(items: List<PokemonFullDataSchema>, lastState: PokemonsScreenState)
        : PokemonsScreenState(ArrayList(items), lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.updateItems(list)
        }
    }

    class Error(val error: Throwable?, lastState: PokemonsScreenState)
        : PokemonsScreenState(lastState.list, lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            super.visit(screen)

            if (!isHandled) {
                screen.showError(error)
                isHandled = true
            }
        }
    }

    class ShowDetails(val id: Long?, lastState: PokemonsScreenState)
        : PokemonsScreenState(lastState.list, lastState.availableFilters, ArrayList(lastState.activeFilters)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            super.visit(screen)

            if (!isHandled) {
                screen.proceedToNextScreen(id)
                isHandled = true
            }
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
