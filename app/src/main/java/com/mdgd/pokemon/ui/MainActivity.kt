package com.mdgd.pokemon.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.mdgd.mvi.HostActivity
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsFragmentArgs
import com.mdgd.pokemon.ui.pokemons.PokemonsContract
import com.mdgd.pokemon.ui.splash.SplashContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : HostActivity(), SplashContract.Host, PokemonsContract.Host, PokemonDetailsContract.Host {
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun proceedToPokemonsScreen() {
        navController!!.navigate(R.id.action_splashFragment_to_pokemonsFragment, null, // doesn't work from xml...
            NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
        )
    }

    override fun proceedToPokemonScreen(pokemonId: Long?) {
        navController!!.navigate(R.id.action_pokemonsFragment_to_pokemonDetailsFragment, PokemonDetailsFragmentArgs(pokemonId
            ?: -1).toBundle())
    }
}
