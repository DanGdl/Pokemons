package com.mdgd.pokemon.ui.pokemon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mdgd.pokemon.models.repo.PokemonsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    fun test_Dummy() {

    }
}
