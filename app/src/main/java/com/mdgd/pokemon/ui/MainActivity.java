package com.mdgd.pokemon.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.mdgd.mvi.HostActivity;
import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.error.ErrorFragment;
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract;
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsFragmentArgs;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;
import com.mdgd.pokemon.ui.splash.SplashContract;

public class MainActivity extends HostActivity implements SplashContract.Host, PokemonsContract.Host, PokemonDetailsContract.Host {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public void proceedToPokemonsScreen() {
        navController.navigate(R.id.action_splashFragment_to_pokemonsFragment
                , null
                // doesn't work from xml...
                , new NavOptions.Builder()
                        .setPopUpTo(R.id.splashFragment, true)
                        .build()
        );
    }

    @Override
    public void proceedToPokemonScreen(long id) {
        navController.navigate(R.id.action_pokemonsFragment_to_pokemonDetailsFragment,
                new PokemonDetailsFragmentArgs.Builder().setPokemonId(id).build().toBundle()
        );
    }

    @Override
    public void showError(Throwable error) {
        if (error != null) {
            error.printStackTrace();
        }
        if (getSupportFragmentManager().findFragmentByTag("error") == null) {
            final ErrorFragment errorFragment = ErrorFragment.newInstance(R.string.dialog_error_title, R.string.dialog_error_message);
            errorFragment.setError(error);
            errorFragment.show(getSupportFragmentManager(), "error");
        }
    }
}
