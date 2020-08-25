package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.pokemon.ui.arch.FragmentContract;

public class PokemonsContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonsScreenState> {
    }

    public interface View extends FragmentContract.View {
    }

    public interface Host extends FragmentContract.Host {
    }

    public interface Router {
        void proceedToNextScreen();
    }
}
