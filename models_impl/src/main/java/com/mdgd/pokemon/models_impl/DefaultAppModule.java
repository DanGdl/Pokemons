package com.mdgd.pokemon.models_impl;

import android.content.Context;

import com.mdgd.pokemon.models.AppModule;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.filters.StatsFilter;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.cache.PokemonsCache;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.network.Network;
import com.mdgd.pokemon.models_impl.cache.CacheImpl;
import com.mdgd.pokemon.models_impl.filters.StatsFiltersFactory;
import com.mdgd.pokemon.models_impl.repo.PokemonsRepository;
import com.mdgd.pokemon.models_impl.repo.cache.PokemonsCacheImpl;
import com.mdgd.pokemon.models_impl.repo.dao.PokemonsDaoImpl;
import com.mdgd.pokemon.models_impl.repo.network.PokemonsNetwork;

public class DefaultAppModule implements AppModule {
    private final Context app;
    private final Cache cache;
    private final PokemonsCache pokemonsCache;

    public DefaultAppModule(Context app) {
        this.app = app;
        cache = new CacheImpl();
        pokemonsCache = new PokemonsCacheImpl();
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
        return new PokemonsRepository(getPokemonsDao(), getPokemonsNetwork(), pokemonsCache);
    }

    public Cache getCache() {
        return cache;
    }

    public StatsFilter getStatsFilters() {
        return new StatsFiltersFactory();
    }
}
