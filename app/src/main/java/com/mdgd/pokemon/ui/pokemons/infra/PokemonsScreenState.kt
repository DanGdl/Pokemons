package com.mdgd.pokemon.ui.pokemons.infra

import com.mdgd.mvi.ScreenState
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.PokemonsContract
import java.util.*

class PokemonsScreenState : ScreenState<PokemonsContract.View> {
    private val list: List<PokemonFullDataSchema>?
    private val action: Int
    private val error: Throwable?

    constructor(action: Int) {
        this.action = action
        list = null
        error = null
    }

    constructor(action: Int, list: List<PokemonFullDataSchema>?) {
        this.action = action
        this.list = list
        error = null
    }

    constructor(action: Int, error: Throwable?) {
        this.action = action
        list = LinkedList()
        this.error = error
    }

    override fun visit(screen: PokemonsContract.View) {
        if (LOADING == action) {
            screen.showProgress()
            return
        }
        screen.hideProgress()
        when (action) {
            SET_DATA -> {
                screen.setItems(list!!)
            }
            ADD_DATA -> {
                screen.addItems(list!!)
            }
            UPDATE_DATA -> {
                screen.updateItems(list!!)
            }
            ERROR -> {
                screen.showError(error)
            }
        }
    }

    fun isError(): Boolean {
        return ERROR == action
    }

    companion object {
        private const val SET_DATA = 1
        private const val ADD_DATA = 2
        private const val ERROR = 3
        private const val LOADING = 4
        private const val UPDATE_DATA = 5

        fun createSetDataState(list: List<PokemonFullDataSchema>?): PokemonsScreenState {
            return PokemonsScreenState(SET_DATA, list)
        }

        fun createUpdateDataState(list: List<PokemonFullDataSchema>?): PokemonsScreenState {
            return PokemonsScreenState(UPDATE_DATA, list)
        }

        fun createAddDataState(list: List<PokemonFullDataSchema>?): PokemonsScreenState {
            return PokemonsScreenState(ADD_DATA, list)
        }

        fun createErrorState(error: Throwable?): PokemonsScreenState {
            return PokemonsScreenState(ERROR, error)
        }

        fun createLoadingState(): PokemonsScreenState {
            return PokemonsScreenState(LOADING)
        }
    }
}
