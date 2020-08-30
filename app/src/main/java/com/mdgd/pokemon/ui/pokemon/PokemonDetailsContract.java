package com.mdgd.pokemon.ui.pokemon;

import com.mdgd.pokemon.ui.arch.FragmentContract;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;

import java.util.List;

public class PokemonDetailsContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonDetailsScreenState> {
    }

    public interface View extends FragmentContract.View {
        void setItems(List<PokemonProperty> items);
    }

    public interface Host extends FragmentContract.Host {
    }
}
