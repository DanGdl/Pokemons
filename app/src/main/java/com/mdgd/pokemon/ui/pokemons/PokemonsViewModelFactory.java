package com.mdgd.pokemon.ui.pokemons;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PokemonsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final PokemonsContract.Router router;

    public PokemonsViewModelFactory(PokemonsContract.Router router) {
        this.router = router;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonsViewModel.class) {
            return (T) new PokemonsViewModel(router);
        }
        return null;
    }
}
