package com.mdgd.pokemon.ui;

import android.os.Bundle;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.arch.HostActivity;
import com.mdgd.pokemon.ui.error.ErrorFragment;
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract;
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsFragment;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;
import com.mdgd.pokemon.ui.pokemons.PokemonsFragment;
import com.mdgd.pokemon.ui.splash.SplashContract;
import com.mdgd.pokemon.ui.splash.SplashFragment;

public class MainActivity extends HostActivity implements SplashContract.Host, PokemonsContract.Host, PokemonDetailsContract.Host {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().getFragments().isEmpty()) {
            addFragment(SplashFragment.newInstance());
        }
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void proceedToPokemonsScreen() {
        replaceFragment(PokemonsFragment.newInstance());
    }

    @Override
    public void proceedToPokemonScreen() {
        replaceFragment(PokemonDetailsFragment.newInstance(), true, "pokemon");
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
