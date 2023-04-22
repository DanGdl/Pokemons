package com.mdgd.pokemon.ui.pokemons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.mvi.states.ScreenState
import com.mdgd.mvi.util.DispatchersHolder
import com.mdgd.pokemon.MainDispatcherRule
import com.mdgd.pokemon.Mocks
import com.mdgd.pokemon.TestSuit
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.schemas.Stat
import com.mdgd.pokemon.models_impl.filters.StatsFiltersFactory
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenEffect
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val PAGE_SIZE = 5
    private lateinit var model: PokemonsViewModel
    private lateinit var repo: PokemonsRepo
    private lateinit var dispatchers: DispatchersHolder
    private lateinit var filtersFactory: StatsFilter

    @Before
    fun setup() {
        dispatchers = Mockito.mock(DispatchersHolder::class.java)
        Mockito.`when`(dispatchers.getIO()).thenReturn(Dispatchers.Unconfined)
        Mockito.`when`(dispatchers.getMain()).thenReturn(Dispatchers.Unconfined)

        repo = Mockito.mock(PokemonsRepo::class.java)
        filtersFactory = Mockito.mock(StatsFilter::class.java)
        model = PokemonsViewModel(repo, filtersFactory, dispatchers)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(repo)
        Mockito.verifyNoMoreInteractions(filtersFactory)
    }

    @Test
    fun testSetup_NotingHappened() = runBlocking {
        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)
        model.onStateChanged(Lifecycle.Event.ON_RESUME)
        model.onStateChanged(Lifecycle.Event.ON_PAUSE)
        model.onStateChanged(Lifecycle.Event.ON_STOP)
        model.onStateChanged(Lifecycle.Event.ON_DESTROY)
        model.onStateChanged(Lifecycle.Event.ON_ANY)


        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchError() = runBlocking {
        val error = RuntimeException("TestError")
        Mockito.`when`(repo.getPage(0)).thenThrow(error)
        Mockito.`when`(repo.getPokemons()).thenReturn(ArrayList())
        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())

        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val stateCaptor = argumentCaptor<PokemonsScreenState>()
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val actionCaptor = argumentCaptor<PokemonsScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(2)).onChanged(stateCaptor.capture())
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())

        var loadingCounter = 0
        var updatesCounter = 0
        var errorCounter = 0
        var scrollCounter = 0
        for (state in stateCaptor.allValues) {
            if (state.isProgressVisible) {
                loadingCounter += 1
            } else if (state.activeFilters.isEmpty()) {
                Assert.assertTrue(state.list.isEmpty())
                updatesCounter += 1
            }
        }
        for (action in actionCaptor.allValues) {
            when (action) {
                is PokemonsScreenEffect.Error -> {
                    errorCounter += 1
                    Assert.assertEquals(error.message, action.error?.message)
                }
                is PokemonsScreenEffect.ScrollToStart -> scrollCounter += 1
                else -> {}
            }
        }

        Assert.assertEquals(1, loadingCounter)
        Assert.assertEquals(1, updatesCounter)
        Assert.assertEquals(1, errorCounter)
        Assert.assertEquals(1, scrollCounter)

        Mockito.verify(repo, Mockito.times(1)).getPage(0)
        Mockito.verify(repo, Mockito.times(1)).getPokemons()
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun test_OnItemClick() = runBlocking {
        val testId = 1L
        val pokemon = PokemonFullDataSchema()
        pokemon.pokemonSchema = PokemonSchema()
        pokemon.pokemonSchema?.id = testId

        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())

        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val actionCaptor = argumentCaptor<PokemonsScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onItemClicked(pokemon)


        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        val capturedAction = actionCaptor.firstValue
        Assert.assertTrue(capturedAction is PokemonsScreenEffect.ShowDetails)
        Assert.assertEquals(testId, (capturedAction as PokemonsScreenEffect.ShowDetails).id)
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_Ok() = runBlocking {
        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val stateCaptor = argumentCaptor<PokemonsScreenState>()
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val actionCaptor = argumentCaptor<PokemonsScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)

        Mockito.`when`(filtersFactory.getAvailableFilters()).thenReturn(listOf())
        val pokemons = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).thenReturn(ArrayList(pokemons))
            pokemons
        }


        model.onStateChanged(Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(3)).onChanged(stateCaptor.capture())
        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.firstValue is PokemonsScreenEffect.ScrollToStart)

        var loadingCounter = 0
        var setsCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            if (state.isProgressVisible) {
                loadingCounter += 1
            } else if (setsCounter == 1 && state.list.isNotEmpty() && state.activeFilters.isEmpty()) {
                Assert.assertEquals(pokemons, state.list)
                updatesCounter += 1
            } else if (state.list.isNotEmpty() && state.activeFilters.isEmpty()) {
                setsCounter += 1
                Assert.assertEquals(pokemons, state.list)
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
        model.getEffectObservable().removeObserver(actionObserverMock)
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
        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val stateCaptor = argumentCaptor<PokemonsScreenState>()
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val actionCaptor = argumentCaptor<PokemonsScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)

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


        model.onStateChanged(Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)

        model.loadPage(1)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(5)).onChanged(stateCaptor.capture())
        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.firstValue is PokemonsScreenEffect.ScrollToStart)

        var loadingCounter = 0
        var setsCounter = 0
        var addingsCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            if (state.isProgressVisible) {
                loadingCounter += 1
            } else if (setsCounter != 0 && state.list.isNotEmpty() && state.list == list && state.activeFilters.isEmpty()) {
                Assert.assertEquals(list, state.list)
                addingsCounter += 1
            } else if (setsCounter != 0 && state.list.isNotEmpty() && state.activeFilters.isEmpty()) {
                Assert.assertEquals(page1, state.list)
                updatesCounter += 1
            } else if (state.list.isNotEmpty() && state.activeFilters.isEmpty()) {
                setsCounter += 1
                Assert.assertEquals(page1, state.list)
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
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun test_Filter_Add() = runBlocking {
        Mockito.`when`(filtersFactory.getAvailableFilters())
            .thenReturn(listOf(FilterData.FILTER_ATTACK, FilterData.FILTER_SPEED))
        Mockito.`when`(filtersFactory.getFilters()).thenReturn(StatsFiltersFactory().getFilters())
        val testFilter = FilterData.FILTER_ATTACK

        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val stateCaptor = argumentCaptor<PokemonsScreenState>()
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val actionCaptor = argumentCaptor<PokemonsScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)

        val page1 = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).then { ArrayList(page1) }
            page1
        }


        model.onStateChanged(Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)

        model.sort(testFilter)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(5)).onChanged(stateCaptor.capture())
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        for (effect in actionCaptor.allValues) {
            Assert.assertTrue(effect is PokemonsScreenEffect.ScrollToStart)
        }

        var loadingCounter = 0
        var setsCounter = 0
        var filtersCounter = 0
        var updatesCounter = 0
        for (state in stateCaptor.allValues) {
            if (state.isProgressVisible) {
                loadingCounter += 1
            } else if (filtersCounter == 0 && state.activeFilters.isNotEmpty()) {
                filtersCounter += 1
                val items = state.list
                Assert.assertEquals(PAGE_SIZE, items.size)
                Assert.assertTrue(state.activeFilters.contains(testFilter))
            } else if (setsCounter != 0 && state.list.isNotEmpty()
                && ((updatesCounter == 0 && state.activeFilters.isEmpty()) || state.activeFilters.isNotEmpty())
            ) {
                updatesCounter += 1
                if (updatesCounter == 1) {
                    Assert.assertEquals(page1, state.list)
                } else {
                    val items = state.list
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
            } else if (state.list.isNotEmpty() && state.activeFilters.isEmpty()) {
                setsCounter += 1
                Assert.assertEquals(page1, state.list)
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
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun test_Filter_Remove() = runBlocking {
        Mockito.`when`(filtersFactory.getAvailableFilters())
            .thenReturn(listOf(FilterData.FILTER_ATTACK, FilterData.FILTER_SPEED))
        Mockito.`when`(filtersFactory.getFilters()).thenReturn(StatsFiltersFactory().getFilters())
        val testFilter = FilterData.FILTER_ATTACK

        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val stateCaptor = argumentCaptor<PokemonsScreenState>()
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<PokemonsContract.View>>
        val actionCaptor = argumentCaptor<PokemonsScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)

        val page1 = getPage(0)
        Mockito.`when`(repo.getPage(0)).then {
            Mockito.`when`(repo.getPokemons()).then { ArrayList(page1) }
            page1
        }


        model.onStateChanged(Lifecycle.Event.ON_CREATE)
        model.loadPage(0)
        Thread.sleep(TestSuit.DELAY)

        model.sort(testFilter)
        Thread.sleep(TestSuit.DELAY)

        model.sort(testFilter)
        Thread.sleep(TestSuit.DELAY)


        Mockito.verify(observerMock, Mockito.times(7)).onChanged(stateCaptor.capture())
        Mockito.verify(actionObserverMock, Mockito.times(3)).onChanged(actionCaptor.capture())
        for (effect in actionCaptor.allValues) {
            Assert.assertTrue(effect is PokemonsScreenEffect.ScrollToStart)
        }

        var loadingCounter = 0
        var setsCounter = 0
        var filtersCounter = 0
        var updatesCounter = 0
        for (idx in stateCaptor.allValues.indices) {
            val state = stateCaptor.allValues[idx]
            if (state.isProgressVisible) {
                loadingCounter += 1
            } else if (idx == 3 || idx == 5) {
                filtersCounter += 1
                val items = state.list
                Assert.assertEquals(PAGE_SIZE, items.size)
                if (filtersCounter == 1) {
                    Assert.assertTrue(state.activeFilters.contains(testFilter))
                } else {
                    Assert.assertFalse(state.activeFilters.contains(testFilter))
                }
            } else if (idx == 2 || idx == 4 || idx == 6) {
                updatesCounter += 1
                if (updatesCounter == 1) {
                    Assert.assertEquals(page1, state.list)
                } else if (updatesCounter == 2) {
                    val items = state.list
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
                } else if (updatesCounter == 3) {
                    val items = state.list
                    Assert.assertEquals(page1, items)
                    Assert.assertEquals(PAGE_SIZE, items.size)
                }
            } else if (idx == 1) {
                setsCounter += 1
                Assert.assertEquals(page1, state.list)
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
        model.getEffectObservable().removeObserver(actionObserverMock)
    }
}
