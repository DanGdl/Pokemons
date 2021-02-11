package com.mdgd.pokemon.ui.pokemon;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.models.AppModule;

public class PokemonDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppModule appComponent;

    public PokemonDetailsViewModelFactory(AppModule appComponent) {
        this.appComponent = appComponent;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonDetailsViewModel.class) {
            return (T) new PokemonDetailsViewModel(appComponent.getPokemonsRepo());
        }
        return null;
    }
}
