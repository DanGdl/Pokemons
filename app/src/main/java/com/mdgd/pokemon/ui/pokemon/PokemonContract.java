package com.mdgd.pokemon.ui.pokemon;

import com.mdgd.pokemon.ui.arch.FragmentContract;

public class PokemonContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonScreenState> {
    }

    public interface View extends FragmentContract.View {
    }

    public interface Host extends FragmentContract.Host {
    }
}
