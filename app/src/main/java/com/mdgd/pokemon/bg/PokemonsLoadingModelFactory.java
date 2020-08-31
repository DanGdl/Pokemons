package com.mdgd.pokemon.bg;

import com.mdgd.pokemon.PokemonsApp;
import com.mdgd.pokemon.models.AppComponent;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.PokemonsRepo;

import javax.inject.Inject;

public class PokemonsLoadingModelFactory {

    @Inject
    public PokemonsRepo repo;

    @Inject
    public Cache cache;

    public PokemonsLoadingModelFactory() {
        final AppComponent appComponent = PokemonsApp.getInstance().getAppComponent();
        appComponent.injectPokemonsRepo(this);
        appComponent.injectPokemonsCache(this);
    }

    public LoadPokemonsContract.ServiceModel create() {
        return new LoadPokemonsModel(repo, cache);
    }
}
