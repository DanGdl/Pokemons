package com.mdgd.pokemon.ui;

import android.os.Bundle;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.arch.HostActivity;
import com.mdgd.pokemon.ui.pokemon.PokemonContract;
import com.mdgd.pokemon.ui.pokemon.PokemonFragment;
import com.mdgd.pokemon.ui.pokemons.PokemonsContract;
import com.mdgd.pokemon.ui.pokemons.PokemonsFragment;
import com.mdgd.pokemon.ui.splash.SplashContract;
import com.mdgd.pokemon.ui.splash.SplashFragment;

public class MainActivity extends HostActivity implements SplashContract.Host, PokemonsContract.Host, PokemonContract.Host {

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
        replaceFragment(PokemonFragment.newInstance(), true, "pokemon");
    }

    @Override
    public void showError(Throwable error) {
        if (error != null) {
            error.printStackTrace();
        }
        // todo impl error dialog
    }
}
