package com.mdgd.pokemon.ui.pokemons;

import com.mdgd.mvi.fragments.FragmentContract;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.pokemons.adapter.Pokemon;

import java.util.List;

public class PokemonsContract {

    public interface ViewModel extends FragmentContract.ViewModel<View> {
        void reload();

        void loadPage(int page);

        void sort(String filter);

        void onItemClicked(PokemonFullDataSchema pokemon);

    }

    public interface View extends FragmentContract.View {
        void setProgressVisibility(boolean isVisible);

        void setItems(List<Pokemon> list);

        void showError(Throwable error);

        void proceedToNextScreen(long id);

        void updateFilterButtons(boolean isActive, String filter);
    }

    public interface Host extends FragmentContract.Host {
        void proceedToPokemonScreen(long id);

        void showError(Throwable error);
    }
}
