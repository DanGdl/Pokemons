package com.mdgd.pokemon.ui.pokemons

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.infra.FilterData
import org.junit.Assert
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
    fun test_LoadingState() {
        val list = ArrayList<PokemonFullDataSchema>()

        val state = PokemonsScreenState.Loading(PokemonsScreenState.SetData(list))
        state.visit(view)

        Mockito.verify(view, Mockito.times(1)).setItems(list)
        Mockito.verify(view, Mockito.times(1)).showProgress()

        state.visit(view)
        Mockito.verify(view, Mockito.times(2)).setItems(list)
        verifyNoMoreInteractions()
    }

    @Test
    fun test_SetDataState() {
        val list = ArrayList<PokemonFullDataSchema>()

        val state = PokemonsScreenState.SetData(list)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).setItems(list)
        Mockito.verify(view, Mockito.times(1)).hideProgress()

        state.visit(view)
        Mockito.verify(view, Mockito.times(2)).setItems(list)
        Mockito.verify(view, Mockito.times(2)).hideProgress()
        verifyNoMoreInteractions()
    }

    @Test
    fun test_AddDataState() {
        val list = ArrayList<PokemonFullDataSchema>()
        val list2 = listOf(PokemonFullDataSchema())
        val listCaptor = com.nhaarman.mockitokotlin2.argumentCaptor<List<PokemonFullDataSchema>>()

        val state = PokemonsScreenState.AddData(list2, PokemonsScreenState.SetData(list))

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).setItems(listCaptor.capture())
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        val capturedList = listCaptor.firstValue
        Assert.assertEquals(1, capturedList.size)
        Assert.assertEquals(list2[0], capturedList[0])

        verifyNoMoreInteractions()
    }

    @Test
    fun test_UpdateDataState() {
        val list = listOf(PokemonFullDataSchema())
        val listCaptor = com.nhaarman.mockitokotlin2.argumentCaptor<List<PokemonFullDataSchema>>()

        val state = PokemonsScreenState.UpdateData(list)

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).updateItems(listCaptor.capture())
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        val capturedList = listCaptor.firstValue
        Assert.assertEquals(1, capturedList.size)
        Assert.assertEquals(list[0], capturedList[0])

        verifyNoMoreInteractions()
    }

    @Test
    fun test_ErrorState() {
        val list = ArrayList<PokemonFullDataSchema>()
        val error = Throwable("TestError")

        val state = PokemonsScreenState.Error(error, PokemonsScreenState.SetData(list))

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).setItems(list)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).showError(error)

        state.visit(view)
        Mockito.verify(view, Mockito.times(2)).setItems(list)
        Mockito.verify(view, Mockito.times(2)).hideProgress()

        verifyNoMoreInteractions()
    }

    @Test
    fun test_ShowDetailsState() {
        val list = ArrayList<PokemonFullDataSchema>()
        val pokemonId = 1L

        val state = PokemonsScreenState.ShowDetails(pokemonId, PokemonsScreenState.SetData(list))

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).setItems(list)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).proceedToNextScreen(pokemonId)

        state.visit(view)
        Mockito.verify(view, Mockito.times(2)).setItems(list)
        Mockito.verify(view, Mockito.times(2)).hideProgress()

        verifyNoMoreInteractions()
    }

    @Test
    fun test_ChangeFilterState() {
        val list = ArrayList<PokemonFullDataSchema>()
        val isActive = true
        val filter = FilterData.FILTER_ATTACK

        val state = PokemonsScreenState.ChangeFilterState(isActive, filter, PokemonsScreenState.SetData(list))

        state.visit(view)
        Mockito.verify(view, Mockito.times(1)).setItems(list)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).updateFilterButtons(isActive, filter)

        state.visit(view)
        Mockito.verify(view, Mockito.times(2)).setItems(list)
        Mockito.verify(view, Mockito.times(2)).hideProgress()

        verifyNoMoreInteractions()
    }
}
