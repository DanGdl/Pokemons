package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.mvi.FragmentContract;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.pokemons.infra.FilterData;
import com.mdgd.pokemon.ui.pokemons.infra.PokemonsScreenState;

import java.util.List;

public class PokemonsContract {

    public interface ViewModel extends FragmentContract.ViewModel<PokemonsScreenState> {
        void reload();

        void loadPage(int page);

        void sort(FilterData filterData);

        void onItemClicked(PokemonFullDataSchema pokemon);

    }

    public interface View extends FragmentContract.View {
        void showProgress();

        void hideProgress();

        void setItems(List<PokemonFullDataSchema> list);

        void addItems(List<PokemonFullDataSchema> list);

        void updateItems(List<PokemonFullDataSchema> list);

        void showError(Throwable error);
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPokemonScreen();

        void showError(Throwable error);
    }

    public interface Router {
        void proceedToNextScreen();
    }
}
