package com.mdgd.pokemon.models;

import android.content.Context;

import com.mdgd.pokemon.models.dao.pokemons.PokemonsDao;
import com.mdgd.pokemon.models.network.Network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context app;
    private final Network network = null;
    private PokemonsDao pokemonsDao;

    public AppModule(Context app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context getApp() {
        return app;
    }

    @Provides
    @Singleton
    public Network getNetwork() {
        return network;
    }

    @Provides
    @Singleton
    public PokemonsDao getPokemonsDao() {
        return pokemonsDao;
    }
}
