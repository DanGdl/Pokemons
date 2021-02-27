package com.mdgd.pokemon.models_impl

import android.content.Context
import com.mdgd.pokemon.models.AppModule
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models_impl.cache.CacheImpl
import com.mdgd.pokemon.models_impl.filters.StatsFiltersFactory
import com.mdgd.pokemon.models_impl.repo.PokemonsRepository
import com.mdgd.pokemon.models_impl.repo.cache.PokemonsCacheImpl
import com.mdgd.pokemon.models_impl.repo.dao.PokemonsDaoImpl
import com.mdgd.pokemon.models_impl.repo.network.PokemonsNetwork

class DefaultAppModule(val app: Context) : AppModule {
    private val cache: Cache = CacheImpl()
    private val pokemonCache = PokemonsCacheImpl()

    override fun getAppContext(): Context {
        return app
    }

    override fun getPokemonsNetwork(): Network {
        return PokemonsNetwork()
    }

    override fun getPokemonsDao(): PokemonsDao {
        return PokemonsDaoImpl(app)
    }

    override fun getPokemonsRepo(): PokemonsRepo {
        return PokemonsRepository(getPokemonsDao(), getPokemonsNetwork(), pokemonCache)
    }

    override fun getCache(): Cache {
        return cache
    }

    override fun getFiltersFactory(): StatsFilter {
        return StatsFiltersFactory()
    }
}
