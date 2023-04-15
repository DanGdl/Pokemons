package com.mdgd.pokemon.ui.pokemons

import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.runBlocking
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
    fun test_LoadingState() = runBlocking {
        val list = ArrayList<PokemonFullDataSchema>()
        val filters = ArrayList<String>()
        val prevState = PokemonsScreenState.SetData(list, filters)
        val state = PokemonsScreenState.Loading()

        state.merge(prevState)
        state.visit(view)


        Mockito.verify(view, Mockito.times(1)).showProgress()
        Mockito.verify(view, Mockito.times(1)).setItems(list)
        verifyNoMoreInteractions()
    }

    @Test
    fun test_SetDataState() = runBlocking {
        val list = ArrayList<PokemonFullDataSchema>()
        val filters = ArrayList<String>()
        val state = PokemonsScreenState.SetData(list, filters)

        state.visit(view)

        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).setItems(list)
        verifyNoMoreInteractions()
    }

    @Test
    fun test_AddDataState() = runBlocking {
        val list = ArrayList<PokemonFullDataSchema>()
        val filters = ArrayList<String>()
        val prevState = PokemonsScreenState.SetData(list, filters)

        val list2 = listOf(PokemonFullDataSchema())
        val listCaptor = argumentCaptor<List<PokemonFullDataSchema>>()

        val state = PokemonsScreenState.AddData(list2)


        state.merge(prevState)
        state.visit(view)


        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).setItems(listCaptor.capture())
        val capturedList = listCaptor.firstValue
        Assert.assertEquals(1, capturedList.size)
        Assert.assertEquals(list2[0], capturedList[0])

        verifyNoMoreInteractions()
    }

    @Test
    fun test_UpdateDataState() = runBlocking {
        val list = ArrayList<PokemonFullDataSchema>()
        val filters = ArrayList<String>()
        val prevState = PokemonsScreenState.SetData(list, filters)

        val newList = listOf(PokemonFullDataSchema())
        val listCaptor = argumentCaptor<List<PokemonFullDataSchema>>()

        val state = PokemonsScreenState.UpdateData(newList)


        state.merge(prevState)
        state.visit(view)


        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(1)).setItems(listCaptor.capture())
        val capturedList = listCaptor.firstValue
        Assert.assertEquals(1, capturedList.size)
        Assert.assertEquals(newList[0], capturedList[0])

        verifyNoMoreInteractions()
    }

    @Test
    fun test_ChangeFilterState() = runBlocking {
        val list = ArrayList<PokemonFullDataSchema>()
        val availableFilters = listOf(FilterData.FILTER_ATTACK, FilterData.FILTER_SPEED)
        val prevState = PokemonsScreenState.SetData(list, availableFilters)

        val filter = listOf(FilterData.FILTER_ATTACK)
        val activeStateCaptor = argumentCaptor<Boolean>()
        val filterTypeCaptor = argumentCaptor<String>()

        var state: PokemonsScreenState = PokemonsScreenState.ChangeFilterState(filter)

        state = state.merge(prevState)
        state.visit(view)


        Mockito.verify(view, Mockito.times(1)).setItems(list)
        Mockito.verify(view, Mockito.times(1)).hideProgress()
        Mockito.verify(view, Mockito.times(2)).updateFilterButtons(activeStateCaptor.capture(), filterTypeCaptor.capture())
        for (i in 0..1) {
            Assert.assertEquals(availableFilters[i], filterTypeCaptor.allValues[i])
            Assert.assertEquals(availableFilters[i] == filter[0], activeStateCaptor.allValues[i])
        }

        verifyNoMoreInteractions()
    }
}
