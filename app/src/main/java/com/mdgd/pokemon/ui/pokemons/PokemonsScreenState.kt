package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.ScreenState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

sealed class PokemonsScreenState(protected val list: MutableList<PokemonFullDataSchema>) : ScreenState<PokemonsContract.View> {
    protected var isHandled = false

    public fun getItems(): List<PokemonFullDataSchema> {
        return ArrayList(list)
    }

    class Loading(prevState: PokemonsScreenState) : PokemonsScreenState(prevState.list) {

        override fun visit(screen: PokemonsContract.View) {
            screen.setItems(list)
            if (!isHandled) {
                screen.showProgress()
                isHandled = true
            }
        }
    }

    class SetData(list: List<PokemonFullDataSchema>) : PokemonsScreenState(ArrayList(list)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.setItems(list)
        }
    }

    class AddData(list: List<PokemonFullDataSchema>, prevState: PokemonsScreenState) : PokemonsScreenState(prevState.list) {

        init {
            super.list.addAll(list)
        }

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.setItems(list)
        }
    }

    class UpdateData(items: List<PokemonFullDataSchema>) : PokemonsScreenState(ArrayList(items)) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.updateItems(list)
        }
    }

    class Error(val error: Throwable?, prevState: PokemonsScreenState) : PokemonsScreenState(prevState.list) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.setItems(list)

            if (!isHandled) {
                screen.showError(error)
                isHandled = true
            }
        }
    }

    class ShowDetails(val id: Long?, lastState: PokemonsScreenState) : PokemonsScreenState(lastState.list) {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.setItems(list)

            if (!isHandled) {
                screen.proceedToNextScreen(id)
                isHandled = true
            }
        }
    }

    class ChangeFilterState(val activateFilter: Boolean, val filter: String, lastState: PokemonsScreenState) : PokemonsScreenState(lastState.list) {
        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.setItems(list)

            if (!isHandled) {
                screen.updateFilterButtons(activateFilter, filter)
                isHandled = true
            }
        }
    }
}
