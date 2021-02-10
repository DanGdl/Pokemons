package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.ScreenState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

sealed class PokemonsScreenState : ScreenState<PokemonsContract.View> {


    class Loading : PokemonsScreenState() {

        override fun visit(screen: PokemonsContract.View) {
            screen.showProgress()
        }

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class SetData(private val list: List<PokemonFullDataSchema>) : PokemonsScreenState() {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.setItems(list)
        }
    }

    class AddData(private val list: List<PokemonFullDataSchema>) : PokemonsScreenState() {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.addItems(list)
        }
    }

    class UpdateData(private val list: List<PokemonFullDataSchema>) : PokemonsScreenState() {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.updateItems(list)
        }
    }

    class Error(private val error: Throwable?) : PokemonsScreenState() {

        override fun visit(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.showError(error)
        }
    }
}
