package com.mdgd.pokemon.ui.pokemons

import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenAction
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonsScreenActionTest {
    private lateinit var view: PokemonsContract.View

    @Before
    fun setup() {
        view = Mockito.mock(PokemonsContract.View::class.java)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun test_ErrorState() = runBlockingTest {
        val error = Throwable("TestError")

        val state = PokemonsScreenAction.Error(error)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).showError(error)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()

        verifyNoMoreInteractions()
    }

    @Test
    fun test_ShowDetailsState() = runBlockingTest {
        val pokemonId = 1L

        val state = PokemonsScreenAction.ShowDetails(pokemonId)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).proceedToNextScreen(pokemonId)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()

        verifyNoMoreInteractions()
    }
}
