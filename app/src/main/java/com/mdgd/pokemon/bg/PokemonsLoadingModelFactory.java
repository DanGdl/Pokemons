package com.mdgd.pokemon.bg;

import com.mdgd.pokemon.models.AppModule;

public class PokemonsLoadingModelFactory {

    public AppModule appComponent;

    public PokemonsLoadingModelFactory(AppModule appComponent) {
        this.appComponent = appComponent;
    }

    public LoadPokemonsContract.ServiceModel create() {
        return new LoadPokemonsModel(appComponent.getPokemonsRepo(), appComponent.getCache());
    }
}
