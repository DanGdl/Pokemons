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

public class DefaultAppModule implements AppModule {
    private final Context app;

    public DefaultAppModule(Context app) {
        this.app = app;
    }

    public Context getApp() {
        return app;
    }

    public Network getPokemonsNetwork() {
        return new PokemonsNetwork();
    }

    public PokemonsDao getPokemonsDao() {
        return new PokemonsDaoImpl(app);
    }

    public PokemonsRepo getPokemonsRepo() {
        return new PokemonsRepository(getPokemonsDao(), getPokemonsNetwork());
    }

    public Cache getCache() {
        return new CacheImpl();
    }
}
