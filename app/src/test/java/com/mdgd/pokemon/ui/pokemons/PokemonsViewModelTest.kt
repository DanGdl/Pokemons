package com.mdgd.pokemon.ui.pokemons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.nhaarman.mockitokotlin2.firstValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var model: PokemonsViewModel
    private lateinit var repo: PokemonsRepo

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        repo = Mockito.mock(PokemonsRepo::class.java)
        model = PokemonsViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(repo)
    }

    @Test
    fun testSetup_NotingHappened() {
        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        model.getStateObservable().observeForever(observerMock)

        model.onAny(null, Lifecycle.Event.ON_START)
        model.onAny(null, Lifecycle.Event.ON_RESUME)
        model.onAny(null, Lifecycle.Event.ON_PAUSE)
        model.onAny(null, Lifecycle.Event.ON_STOP)
        model.onAny(null, Lifecycle.Event.ON_DESTROY)
        model.onAny(null, Lifecycle.Event.ON_ANY)

        Mockito.verifyNoMoreInteractions(observerMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
    }

    @Test
    fun testSetup_LaunchError() = runBlocking {
        val error = RuntimeException("TestError")
        Mockito.`when`(repo.getPage(0)).thenThrow(error)
        Mockito.`when`(repo.getPokemons()).thenReturn(ArrayList())

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(2000)

        Mockito.verify(observerMock, Mockito.times(4)).onChanged(stateCaptor.capture())
        for (state in stateCaptor.allValues) {
            when (state) {
                is PokemonsScreenState.Loading -> {
                    Assert.assertTrue(state.getItems().isEmpty())
                }
                is PokemonsScreenState.UpdateData -> {
                    Assert.assertTrue(state.getItems().isEmpty())
                }
                is PokemonsScreenState.Error -> {
                    Assert.assertTrue(state.getItems().isEmpty())
                    Assert.assertEquals(error.message, state.error?.message)
                }
            }
        }

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(1)).getPokemons()
        Mockito.verifyNoMoreInteractions(observerMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
    }

    @Test
    fun test_OnItemClick() {
        val testId = 1L
        val pokemon = PokemonFullDataSchema()
        pokemon.pokemonSchema = PokemonSchema()
        pokemon.pokemonSchema?.id = testId

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)

        model.onItemClicked(pokemon)

        Mockito.verify(observerMock, Mockito.times(1)).onChanged(stateCaptor.capture())
        val capturedState = stateCaptor.firstValue
        Assert.assertTrue(capturedState is PokemonsScreenState.ShowDetails)
        Assert.assertEquals(testId, (capturedState as PokemonsScreenState.ShowDetails).id)
        Mockito.verifyNoMoreInteractions(observerMock)
        verifyNoMoreInteractions()
    }
}
