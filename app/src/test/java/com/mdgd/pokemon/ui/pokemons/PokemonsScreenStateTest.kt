package com.mdgd.pokemon.ui.pokemons

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonsScreenStateTest {
    private lateinit var view: PokemonsContract.View

    @Before
    fun setup() {
        view = Mockito.mock(PokemonsContract.View::class.java)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun test_Dummy() {

    }
}
