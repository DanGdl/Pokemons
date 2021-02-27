package com.mdgd.pokemon

import com.mdgd.pokemon.bg.LoadPokemonsModelTest
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsScreenStateTest
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsViewModelTest
import com.mdgd.pokemon.ui.pokemons.PokemonsScreenStateTest
import com.mdgd.pokemon.ui.pokemons.PokemonsViewModelTest
import com.mdgd.pokemon.ui.splash.SplashScreenStateTest
import com.mdgd.pokemon.ui.splash.SplashViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        SplashScreenStateTest::class,
        SplashViewModelTest::class,

        PokemonDetailsScreenStateTest::class,
        PokemonDetailsViewModelTest::class,

        PokemonsScreenStateTest::class,
        PokemonsViewModelTest::class,

        LoadPokemonsModelTest::class
)
class TestSuit {
    companion object {
        const val DELAY = 1000L
    }
}
