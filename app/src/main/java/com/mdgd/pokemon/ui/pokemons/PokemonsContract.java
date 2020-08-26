package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.pokemon.dto.Pokemon;
import com.mdgd.pokemon.ui.arch.FragmentContract;
import com.mdgd.pokemon.ui.pokemons.dto.FilterData;
import com.mdgd.pokemon.ui.pokemons.dto.PokemonsScreenState;

public class PokemonsContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonsScreenState> {
        void reload();

        void sort(FilterData filterData);

        void onItemClicked(Pokemon pokemon);
    }

    public interface View extends FragmentContract.View {
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPokemonScreen();
    }

    public interface Router {
        void proceedToNextScreen();
    }
}
