package com.mdgd.pokemon.ui.pokemons;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.models.AppModule;

public class PokemonsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppModule appComponent;

    public PokemonsViewModelFactory(AppModule appComponent) {
        this.appComponent = appComponent;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonsViewModel.class) {
            return (T) new PokemonsViewModel(appComponent.getPokemonsRepo());
        }
        return null;
    }
}
