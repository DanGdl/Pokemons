package com.mdgd.pokemon.ui.pokemon;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PokemonViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public PokemonViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonViewModel.class) {
            return (T) new PokemonViewModel();
        }
        return null;
    }
}
