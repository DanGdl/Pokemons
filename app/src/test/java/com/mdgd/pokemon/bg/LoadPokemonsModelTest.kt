package com.mdgd.pokemon.bg

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mdgd.pokemon.TestSuit
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class LoadPokemonsModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var model: LoadPokemonsModel
    private lateinit var repo: PokemonsRepo
    private lateinit var cache: Cache

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cache = Mockito.mock(Cache::class.java)
        repo = Mockito.mock(PokemonsRepo::class.java)
        model = LoadPokemonsModel(repo, cache)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(cache)
        Mockito.verifyNoMoreInteractions(repo)
    }

    @Test
    fun test_LoadPokemonsCrash() = runBlockingTest {
        val initialLoadingAmount = (PokemonsRepo.PAGE_SIZE * 2).toLong()
        val error = RuntimeException("TestError")
        val resultCaptor = com.nhaarman.mockitokotlin2.argumentCaptor<Result<Long>>()
        Mockito.`when`(repo.loadInitialPages(initialLoadingAmount)).thenThrow(error)

        model.load()
        Thread.sleep(TestSuit.DELAY)

        Mockito.verify(repo, Mockito.times(1)).loadInitialPages(initialLoadingAmount)

        Mockito.verify(cache, Mockito.times(1)).putLoadingProgress(resultCaptor.capture())
        val result = resultCaptor.lastValue
        Assert.assertEquals(error, result.getError())
        verifyNoMoreInteractions()
    }

    @Test
    fun test_LoadPokemonsOk() = runBlockingTest {
        val initialLoadingAmount = (PokemonsRepo.PAGE_SIZE * 2).toLong()
        val totalLoadingAmount = initialLoadingAmount * 2
        Mockito.`when`(repo.loadPokemons(initialLoadingAmount)).thenReturn(totalLoadingAmount)
        val resultCaptor = com.nhaarman.mockitokotlin2.argumentCaptor<Result<Long>>()

        model.load()
        Thread.sleep(TestSuit.DELAY)

        Mockito.verify(repo, Mockito.times(1)).loadInitialPages(initialLoadingAmount)

        Mockito.verify(cache, Mockito.times(2)).putLoadingProgress(resultCaptor.capture())
        Assert.assertEquals(initialLoadingAmount, resultCaptor.firstValue.getValue())

        Mockito.verify(repo, Mockito.times(1)).loadPokemons(initialLoadingAmount)
        Mockito.verify(cache, Mockito.times(2)).putLoadingProgress(resultCaptor.capture())
        Assert.assertEquals(totalLoadingAmount, resultCaptor.lastValue.getValue())
        verifyNoMoreInteractions()
    }
}
