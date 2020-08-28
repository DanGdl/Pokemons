package com.mdgd.pokemon.ui.pokemon;

import com.mdgd.pokemon.ui.arch.FragmentContract;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState;

public class PokemonDetailsContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonDetailsScreenState> {
    }

    public interface View extends FragmentContract.View {
    }

    public interface Host extends FragmentContract.Host {
    }
}
