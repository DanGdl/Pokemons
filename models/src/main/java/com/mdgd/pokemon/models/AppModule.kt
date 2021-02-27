package com.mdgd.pokemon.models

import android.content.Context
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.network.Network

interface AppModule {
    fun getAppContext(): Context
    fun getPokemonsNetwork(): Network
    fun getPokemonsDao(): PokemonsDao
    fun getPokemonsRepo(): PokemonsRepo
    fun getCache(): Cache
    fun getFiltersFactory(): StatsFilter;
}
