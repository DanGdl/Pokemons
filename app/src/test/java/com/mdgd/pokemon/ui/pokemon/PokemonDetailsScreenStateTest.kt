package com.mdgd.pokemon.ui.pokemon

import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonDetailsScreenStateTest {
    private lateinit var view: PokemonDetailsContract.View

    @Before
    fun setup() {
        view = Mockito.mock(PokemonDetailsContract.View::class.java)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun testSetDataState() = runBlocking {
        val detailsCaptor = argumentCaptor<List<PokemonProperty>>()
        val list = ArrayList<PokemonProperty>(0)
        PokemonDetailsScreenState.SetData(list).visit(view)

        Mockito.verify(view, Mockito.times(1)).setItems(detailsCaptor.capture())
        Assert.assertEquals(list, detailsCaptor.firstValue)
        verifyNoMoreInteractions()
    }
}
