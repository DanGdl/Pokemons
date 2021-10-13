package com.mdgd.pokemon.ui.pokemons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.pokemon.Mocks
import com.mdgd.pokemon.TestSuit
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.schemas.Stat
import com.mdgd.pokemon.models.util.DispatchersHolder
import com.mdgd.pokemon.models_impl.filters.StatsFiltersFactory
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenAction
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
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
    private val PAGE_SIZE = 5
    private lateinit var model: PokemonsViewModel
    private lateinit var repo: PokemonsRepo
    private lateinit var dispatchers: DispatchersHolder
    private lateinit var filtersFactory: StatsFilter

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        dispatchers = Mockito.mock(DispatchersHolder::class.java)
        Mockito.`when`(dispatchers.getIO()).thenReturn(Dispatchers.Unconfined)
        Mockito.`when`(dispatchers.getMain()).thenReturn(Dispatchers.Unconfined)

        repo = Mockito.mock(PokemonsRepo::class.java)
        filtersFactory = Mockito.mock(StatsFilter::class.java)
        model = PokemonsViewModel(repo, filtersFactory, dispatchers)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(repo)
        Mockito.verifyNoMoreInteractions(filtersFactory)
    }

    @Test
    fun testSetup_NotingHappened() = runBlocking {
        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        model.getActionObservable().observeForever(actionObserverMock)


        model.onAny(null, Lifecycle.Event.ON_START)
        model.onAny(null, Lifecycle.Event.ON_RESUME)
        model.onAny(null, Lifecycle.Event.ON_PAUSE)
        model.onAny(null, Lifecycle.Event.ON_STOP)
        model.onAny(null, Lifecycle.Event.ON_DESTROY)
        model.onAny(null, Lifecycle.Event.ON_ANY)


        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchError() = runBlocking {
        val error = RuntimeException("TestError")
        Mockito.`when`(repo.getPage(0)).thenThrow(error)
        Mockito.`when`(repo.getPokemons()).thenReturn(ArrayList())
        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        val actionCaptor = ArgumentCaptor.forClass(PokemonsScreenAction::class.java)
        model.getActionObservable().observeForever(actionObserverMock)


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(2)).onChanged(stateCaptor.capture())
        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        var loadingCounter = 0
        var updatesCounter = 0
        var errorCounter = 0
        for (state in stateCaptor.allValues) {
            when (state) {
                is PokemonsScreenState.Loading -> {
                    loadingCounter += 1
                }
                is PokemonsScreenState.UpdateData -> {
                    updatesCounter += 1
                    Assert.assertTrue(state.getItems().isEmpty())
                }
            }
        }
        for (action in actionCaptor.allValues) {
            when (action) {
                is PokemonsScreenAction.Error -> {
                    errorCounter += 1
                    Assert.assertEquals(error.message, action.error?.message)
                }
            }
        }

        Assert.assertEquals(1, loadingCounter)
        Assert.assertEquals(1, updatesCounter)
        Assert.assertEquals(1, errorCounter)

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(1)).getPokemons()
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun test_OnItemClick() = runBlocking {
        val testId = 1L
        val pokemon = PokemonFullDataSchema()
        pokemon.pokemonSchema = PokemonSchema()
        pokemon.pokemonSchema?.id = testId

        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        val actionCaptor = ArgumentCaptor.forClass(PokemonsScreenAction::class.java)
        model.getActionObservable().observeForever(actionObserverMock)


        model.onItemClicked(pokemon)


        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        val capturedAction = actionCaptor.firstValue
        Assert.assertTrue(capturedAction is PokemonsScreenAction.ShowDetails)
        Assert.assertEquals(testId, (capturedAction as PokemonsScreenAction.ShowDetails).id)
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_Ok() = runBlocking {
        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        val actionCaptor = ArgumentCaptor.forClass(PokemonsScreenAction::class.java)
        model.getActionObservable().observeForever(actionObserverMock)

        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())
        val pokemons = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).thenReturn(ArrayList(pokemons))
            pokemons
        }


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(3)).onChanged(stateCaptor.capture())
        var loadingCounter = 0
        var setsCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            when (state) {
                is PokemonsScreenState.Loading -> {
                    loadingCounter += 1
                }
                is PokemonsScreenState.SetData -> {
                    setsCounter += 1
                    Assert.assertEquals(pokemons, state.getItems())
                }
                is PokemonsScreenState.UpdateData -> {
                    Assert.assertEquals(pokemons, state.getItems())
                    updatesCounter += 1
                }
            }
        }
        Assert.assertEquals(1, loadingCounter)
        Assert.assertEquals(1, setsCounter)
        Assert.assertEquals(1, updatesCounter)

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(1)).getPokemons()
        Mockito.verify(filtersFactory, Mockito.times(1)).getAvailableFilters()
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }

    private fun getPage(page: Int): List<PokemonFullDataSchema> {
        val pokemons = ArrayList<PokemonFullDataSchema>(PAGE_SIZE)

        for (i in 0 until PAGE_SIZE) {
            val pokemon = Mocks.getPokemon()
            pokemon.pokemonSchema?.id = page + i.toLong()
            for (s in pokemon.stats) {
                s.baseStat = page + i // (PAGE_SIZE * (page + 1)) - (page + i)
            }
            pokemons.add(pokemon)
        }
        return pokemons
    }

    @Test
    fun test_NextPageOk() = runBlocking {
        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        val actionCaptor = ArgumentCaptor.forClass(PokemonsScreenAction::class.java)
        model.getActionObservable().observeForever(actionObserverMock)

        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())

        val page1 = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).thenReturn(ArrayList(page1))
            page1
        }
        val page2 = getPage(1)
        val list = ArrayList(page1)
        Mockito.`when`(repo.getPage(1)).then {
            list.addAll(page2)
            Mockito.`when`(repo.getPokemons()).thenReturn(list)
            page2
        }


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)

        model.loadPage(1)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(5)).onChanged(stateCaptor.capture())

        var loadingCounter = 0
        var setsCounter = 0
        var addingsCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            when (state) {
                is PokemonsScreenState.Loading -> {
                    loadingCounter += 1
                }
                is PokemonsScreenState.SetData -> {
                    setsCounter += 1
                    Assert.assertEquals(page1, state.getItems())
                }
                is PokemonsScreenState.AddData -> {
                    addingsCounter += 1
                    Assert.assertEquals(list, state.getItems())
                }
                is PokemonsScreenState.UpdateData -> {
                    updatesCounter += 1
                    Assert.assertEquals(page1, state.getItems())
                }
            }
        }
        Assert.assertEquals(2, loadingCounter)
        Assert.assertEquals(1, setsCounter)
        Assert.assertEquals(1, addingsCounter)
        Assert.assertEquals(1, updatesCounter)

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(1)).getPage(1)
        Mockito.verify(repo, Mockito.times(1)).getPokemons()
        Mockito.verify(filtersFactory, Mockito.times(1)).getAvailableFilters()
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun test_Filter_Add() = runBlocking {
        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf(FilterData.FILTER_ATTACK, FilterData.FILTER_SPEED))
        Mockito.`when`(filtersFactory.getFilters()).thenReturn(StatsFiltersFactory().getFilters())
        val testFilter = FilterData.FILTER_ATTACK

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        val actionCaptor = ArgumentCaptor.forClass(PokemonsScreenAction::class.java)
        model.getActionObservable().observeForever(actionObserverMock)

        val page1 = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).then { ArrayList(page1) }
            page1
        }


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)

        model.sort(testFilter)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(5)).onChanged(stateCaptor.capture())

        var loadingCounter = 0
        var setsCounter = 0
        var filtersCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            when (state) {
                is PokemonsScreenState.SetData -> {
                    Assert.assertEquals(page1, state.getItems())
                    setsCounter += 1
                }
                is PokemonsScreenState.UpdateData -> {
                    if (updatesCounter == 0) {
                        Assert.assertEquals(page1, state.getItems())
                    } else {
                        val items = state.getItems()
                        Assert.assertEquals(PAGE_SIZE, items.size)
                        Assert.assertNotEquals(page1, items)
                        for ((idx, item) in items.withIndex()) {
                            if (idx == 0) {
                                continue
                            }
                            var prevStat: Stat? = null
                            for (ps in items[idx - 1].stats) {
                                if (testFilter == ps.stat?.name) {
                                    prevStat = ps
                                    break
                                }
                            }

                            var stat: Stat? = null
                            for (ps in item.stats) {
                                if (testFilter == ps.stat?.name) {
                                    stat = ps
                                    break
                                }
                            }
                            Assert.assertTrue(prevStat!!.baseStat!! >= stat!!.baseStat!!)
                        }
                    }
                    updatesCounter += 1
                }
                is PokemonsScreenState.ChangeFilterState -> {
                    filtersCounter += 1
                    val items = state.getItems()
                    Assert.assertEquals(PAGE_SIZE, items.size)
                    Assert.assertTrue(state.getActiveFilters().contains(testFilter))
                }
                is PokemonsScreenState.Loading -> {
                    loadingCounter += 1
                }
            }
        }
        Assert.assertEquals(1, loadingCounter)
        Assert.assertEquals(1, setsCounter)
        Assert.assertEquals(1, filtersCounter)
        Assert.assertEquals(2, updatesCounter)

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(2)).getPokemons()
        Mockito.verify(filtersFactory, Mockito.times(1)).getAvailableFilters()
        Mockito.verify(filtersFactory, Mockito.times(1)).getFilters()
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun test_Filter_Remove() = runBlocking {
        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf(FilterData.FILTER_ATTACK, FilterData.FILTER_SPEED))
        Mockito.`when`(filtersFactory.getFilters()).thenReturn(StatsFiltersFactory().getFilters())
        val testFilter = FilterData.FILTER_ATTACK

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenState>
        val stateCaptor = ArgumentCaptor.forClass(PokemonsScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<PokemonsScreenAction>
        val actionCaptor = ArgumentCaptor.forClass(PokemonsScreenAction::class.java)
        model.getActionObservable().observeForever(actionObserverMock)

        val page1 = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).then { ArrayList(page1) }
            page1
        }


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)

        model.sort(testFilter)
        Thread.sleep(TestSuit.DELAY)

        model.sort(testFilter)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(7)).onChanged(stateCaptor.capture())

        var loadingCounter = 0
        var setsCounter = 0
        var filtersCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            when (state) {
                is PokemonsScreenState.Loading -> {
                    loadingCounter += 1
                }
                is PokemonsScreenState.SetData -> {
                    setsCounter += 1
                    Assert.assertEquals(page1, state.getItems())
                }
                is PokemonsScreenState.ChangeFilterState -> {
                    val items = state.getItems()
                    Assert.assertEquals(PAGE_SIZE, items.size)
                    if (filtersCounter == 0) {
                        Assert.assertTrue(state.getActiveFilters().contains(testFilter))
                    } else {
                        Assert.assertFalse(state.getActiveFilters().contains(testFilter))
                    }
                    filtersCounter += 1
                }
                is PokemonsScreenState.UpdateData -> {
                    if (updatesCounter == 0) {
                        Assert.assertEquals(page1, state.getItems())
                    } else if (updatesCounter == 1) {
                        val items = state.getItems()
                        Assert.assertNotEquals(page1, items)
                        Assert.assertEquals(PAGE_SIZE, items.size)
                        for ((idx, item) in items.withIndex()) {
                            if (idx == 0) {
                                continue
                            }
                            var prevStat: Stat? = null
                            for (ps in items[idx - 1].stats) {
                                if (FilterData.FILTER_ATTACK == ps.stat?.name) {
                                    prevStat = ps
                                    break
                                }
                            }

                            var stat: Stat? = null
                            for (ps in item.stats) {
                                if (FilterData.FILTER_ATTACK == ps.stat?.name) {
                                    stat = ps
                                    break
                                }
                            }
                            Assert.assertTrue(prevStat!!.baseStat!! >= stat!!.baseStat!!)
                        }
                    } else if (updatesCounter == 2) {
                        val items = state.getItems()
                        Assert.assertEquals(page1, items)
                        Assert.assertEquals(PAGE_SIZE, items.size)
                    }
                    updatesCounter += 1
                }
            }
        }
        Assert.assertEquals(1, loadingCounter)
        Assert.assertEquals(1, setsCounter)
        Assert.assertEquals(2, filtersCounter)
        Assert.assertEquals(3, updatesCounter)

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(3)).getPokemons()
        Mockito.verify(filtersFactory, Mockito.times(1)).getAvailableFilters()
        Mockito.verify(filtersFactory, Mockito.times(1)).getFilters()

        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getActionObservable().removeObserver(actionObserverMock)
    }
}
