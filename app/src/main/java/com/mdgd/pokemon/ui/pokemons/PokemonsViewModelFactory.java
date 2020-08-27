package com.mdgd.pokemon.ui.pokemons;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.PokemonsApp;
import com.mdgd.pokemon.models.AppComponent;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.PokemonsRepo;

import javax.inject.Inject;

public class PokemonsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final PokemonsContract.Router router;

    @Inject
    public PokemonsRepo repo;

    @Inject
    public Cache cache;

    public PokemonsViewModelFactory(PokemonsContract.Router router) {
        this.router = router;
        final AppComponent appComponent = PokemonsApp.getInstance().getAppComponent();
        appComponent.injectPokemonsRepo(this);
        appComponent.injectPokemonsCache(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PokemonsViewModel.class) {
            return (T) new PokemonsViewModel(router, repo, cache);
        }
        return null;
    }
}
