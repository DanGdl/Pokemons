package com.mdgd.pokemon.ui.pokemon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.pokemon.Mocks
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.ui.pokemon.dto.ImagePropertyData
import com.mdgd.pokemon.ui.pokemon.dto.LabelPropertyData
import com.mdgd.pokemon.ui.pokemon.dto.TextPropertyData
import com.mdgd.pokemon.ui.pokemon.dto.TitlePropertyData
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenEffect
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PokemonDetailsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var model: PokemonDetailsViewModel
    private lateinit var repo: PokemonsRepo

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        repo = Mockito.mock(PokemonsRepo::class.java)
        model = PokemonDetailsViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(repo)
    }

    @Test
    fun testSetup_NotingHappened() = runBlockingTest {
        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonDetailsScreenState>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<PokemonDetailsScreenEffect>
        model.getEffectObservable().observeForever(actionObserverMock)

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
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchError() = runBlockingTest {
        val error = RuntimeException("TestError")
        Mockito.`when`(repo.getPokemonById(0)).thenThrow(error)

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonDetailsScreenState>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<PokemonDetailsScreenEffect>
        model.getEffectObservable().observeForever(actionObserverMock)

        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.setPokemonId(0)
        Thread.sleep(2000)

        Mockito.verify(repo, Mockito.times(1)).getPokemonById(0)
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchOk() = runBlockingTest {
        val pokemon = Mocks.getPokemon()
        Mockito.`when`(repo.getPokemonById(0)).thenReturn(pokemon)

        val observerMock = Mockito.mock(Observer::class.java) as Observer<PokemonDetailsScreenState>
        val stateCaptor = argumentCaptor<PokemonDetailsScreenState>()
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<PokemonDetailsScreenEffect>
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.setPokemonId(0)
        Thread.sleep(2000)

        Mockito.verify(observerMock, Mockito.times(1)).onChanged(stateCaptor.capture())
        val state = stateCaptor.firstValue
        Assert.assertTrue(state is PokemonDetailsScreenState.SetData)
        Assert.assertEquals(19, (state as PokemonDetailsScreenState.SetData).items.size)

        val statsStart = 5
        val statsEnd = 5 + pokemon.stats.size

        for ((idx, item) in state.items.withIndex()) {
            if (idx == 0) {
                Assert.assertEquals(pokemon.pokemonSchema!!.sprites!!.other!!.officialArtwork!!.frontDefault!!, (item as ImagePropertyData).imageUrl)
            } else if (idx == 1) {
                Assert.assertEquals(R.string.pokemon_detail_name, (item as LabelPropertyData).titleResId)
                Assert.assertEquals(pokemon.pokemonSchema!!.name, item.text)
                Assert.assertEquals("", item.titleStr)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == 2) {
                Assert.assertEquals(R.string.pokemon_detail_height, (item as LabelPropertyData).titleResId)
                Assert.assertEquals(pokemon.pokemonSchema!!.height.toString(), item.text)
                Assert.assertEquals("", item.titleStr)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == 3) {
                Assert.assertEquals(R.string.pokemon_detail_weight, (item as LabelPropertyData).titleResId)
                Assert.assertEquals(pokemon.pokemonSchema!!.weight.toString(), item.text)
                Assert.assertEquals("", item.titleStr)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == 4) {
                Assert.assertEquals(R.string.pokemon_detail_stats, (item as TitlePropertyData).titleResId)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx in statsStart until statsEnd) {
                Assert.assertEquals(0, (item as LabelPropertyData).titleResId)
                Assert.assertEquals(pokemon.stats[idx - 5].baseStat.toString(), item.text)
                Assert.assertEquals(pokemon.stats[idx - 5].stat?.name, item.titleStr)
                Assert.assertEquals(1, item.nestingLevel)
            } else if (idx == statsEnd) {
                Assert.assertEquals(R.string.pokemon_detail_abilities, (item as TitlePropertyData).titleResId)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == statsEnd + 1) {
                val abilitiesSb = StringBuilder()
                for (i in pokemon.abilities.indices) {
                    abilitiesSb.append(pokemon.abilities[i].ability!!.name)
                    if (i < pokemon.abilities.size - 1) {
                        abilitiesSb.append(", ")
                    }
                }
                Assert.assertEquals(abilitiesSb.toString(), (item as TextPropertyData).text)
                Assert.assertEquals(1, item.nestingLevel)
            } else if (idx == statsEnd + 2) {
                Assert.assertEquals(R.string.pokemon_detail_forms, (item as TitlePropertyData).titleResId)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == statsEnd + 3) {
                val formsSb = StringBuilder()
                for (i in pokemon.forms.indices) {
                    formsSb.append(pokemon.forms[i].name)
                    if (i < pokemon.forms.size - 1) {
                        formsSb.append(", ")
                    }
                }
                Assert.assertEquals(formsSb.toString(), (item as TextPropertyData).text)
                Assert.assertEquals(1, item.nestingLevel)
            } else if (idx == statsEnd + 4) {
                Assert.assertEquals(R.string.pokemon_detail_types, (item as TitlePropertyData).titleResId)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == statsEnd + 5) {
                val typesSb = StringBuilder()
                for (i in pokemon.types.indices) {
                    typesSb.append(pokemon.types[i].type?.name)
                    if (i < pokemon.types.size - 1) {
                        typesSb.append(", ")
                    }
                }
                Assert.assertEquals(typesSb.toString(), (item as TextPropertyData).text)
                Assert.assertEquals(1, item.nestingLevel)
            } else if (idx == statsEnd + 6) {
                Assert.assertEquals(R.string.pokemon_detail_game_indicies, (item as TitlePropertyData).titleResId)
                Assert.assertEquals(0, item.nestingLevel)
            } else if (idx == statsEnd + 7) {
                val gameIdxSb = StringBuilder()
                for (i in pokemon.gameIndices.indices) {
                    gameIdxSb.append(pokemon.gameIndices[i].version?.name)
                    if (i < pokemon.gameIndices.size - 1) {
                        gameIdxSb.append(", ")
                    }
                }
                Assert.assertEquals(gameIdxSb.toString(), (item as TextPropertyData).text)
                Assert.assertEquals(1, item.nestingLevel)
            }
        }

        Mockito.verify(repo, Mockito.times(1)).getPokemonById(0)
        Mockito.verifyNoMoreInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }
}
