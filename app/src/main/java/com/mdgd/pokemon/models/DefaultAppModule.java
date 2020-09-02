package com.mdgd.pokemon.models;

import android.content.Context;

import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.cache.CacheImpl;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.PokemonsRepository;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.dao.PokemonsDaoImpl;
import com.mdgd.pokemon.models.repo.network.Network;
import com.mdgd.pokemon.models.repo.network.PokemonsNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DefaultAppModule {
    private final Context app;

    public DefaultAppModule(Context app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context getApp() {
        return app;
    }

    @Provides
    @Singleton
    public Network getPokemonsNetwork() {
        return new PokemonsNetwork();
    }

    @Provides
    @Singleton
    public PokemonsDao getPokemonsDao() {
        return new PokemonsDaoImpl(app);
    }

    @Provides
    @Singleton
    public PokemonsRepo getPokemonsRepo() {
        return new PokemonsRepository(getPokemonsDao(), getPokemonsNetwork());
    }

    @Provides
    @Singleton
    public Cache getCache() {
        return new CacheImpl();
    }
}
