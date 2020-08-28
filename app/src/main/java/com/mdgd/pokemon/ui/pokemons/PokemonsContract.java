package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;
import com.mdgd.pokemon.ui.arch.FragmentContract;
import com.mdgd.pokemon.ui.pokemons.infra.FilterData;
import com.mdgd.pokemon.ui.pokemons.infra.PokemonsScreenState;

public class PokemonsContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonsScreenState> {
        void reload();

        void loadPage(int page);

        void sort(FilterData filterData);

        void onItemClicked(PokemonDetails pokemon);

    }

    public interface View extends FragmentContract.View {
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPokemonScreen();

        void showError(Throwable error);
    }

    public interface Router {
        void proceedToNextScreen();
    }
}
