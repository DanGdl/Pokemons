package com.mdgd.pokemon.models

import android.content.Context
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.cache.CacheImpl
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.PokemonsRepository
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.PokemonsDaoImpl
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.repo.network.PokemonsNetwork

class DefaultAppModule(val app: Context) : AppModule {
    private val cache: Cache

    init {
        cache = CacheImpl()
    }

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
        return PokemonsRepository(getPokemonsDao(), getPokemonsNetwork())
    }

    override fun getCache(): Cache {
        return cache
    }
}
