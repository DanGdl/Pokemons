package com.mdgd.pokemon.ui.pokemon;

import com.mdgd.mvi.fragments.FragmentContract;
import com.mdgd.pokemon.ui.pokemon.items.PokemonProperty;

import java.util.List;

public class PokemonDetailsContract {

    public interface ViewModel extends FragmentContract.ViewModel<View> {
        void setPokemonId(long pokemonId);
    }

    public interface View extends FragmentContract.View {
        void setItems(List<PokemonProperty> items);
    }

    public interface Host extends FragmentContract.Host {
    }
}
