package com.mdgd.pokemon.ui.pokemons

import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenEffect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonsScreenEffectTest {
    private lateinit var view: PokemonsContract.View

    @Before
    fun setup() {
        view = Mockito.mock(PokemonsContract.View::class.java)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun test_ErrorState() = runBlocking {
        val error = Throwable("TestError")

        val state = PokemonsScreenEffect.Error(error)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).showError(error)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()

        verifyNoMoreInteractions()
    }

    @Test
    fun test_ShowDetailsState() = runBlocking {
        val pokemonId = 1L

        val state = PokemonsScreenEffect.ShowDetails(pokemonId)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).proceedToNextScreen(pokemonId)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).hideProgress()

        verifyNoMoreInteractions()
    }
}
