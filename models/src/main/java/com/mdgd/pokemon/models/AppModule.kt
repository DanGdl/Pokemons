package com.mdgd.pokemon.models;

import android.content.Context;

import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.network.Network;

public interface AppModule {

    Context getApp();

    Network getPokemonsNetwork();

    PokemonsDao getPokemonsDao();

    PokemonsRepo getPokemonsRepo();

    Cache getCache();
}
