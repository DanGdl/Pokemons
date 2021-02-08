package com.mdgd.pokemon.ui.pokemons;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.models.AppModule;

public class PokemonsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final PokemonsContract.Router router;
    private final AppModule appComponent;

    public PokemonsViewModelFactory(AppModule appComponent, PokemonsContract.Router router) {
        this.appComponent = appComponent;
        this.router = router;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonsViewModel.class) {
            return (T) new PokemonsViewModel(router, appComponent.getPokemonsRepo(), appComponent.getCache());
        }
        return null;
    }
}
