package com.mdgd.pokemon.ui.pokemon;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.PokemonsApp;
import com.mdgd.pokemon.models.AppComponent;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.PokemonsRepo;

import javax.inject.Inject;

public class PokemonDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @Inject
    public PokemonsRepo repo;

    @Inject
    public Cache cache;

    public PokemonDetailsViewModelFactory() {
        final AppComponent appComponent = PokemonsApp.getInstance().getAppComponent();
        appComponent.injectPokemonsCache(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonDetailsViewModel.class) {
            return (T) new PokemonDetailsViewModel(cache);
        }
        return null;
    }
}
