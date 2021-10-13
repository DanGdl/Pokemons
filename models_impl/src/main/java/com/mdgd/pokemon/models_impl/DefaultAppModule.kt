package com.mdgd.pokemon.models_impl

import android.content.Context
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.util.DispatchersHolder
import com.mdgd.pokemon.models_impl.cache.CacheImpl
import com.mdgd.pokemon.models_impl.filters.StatsFiltersFactory
import com.mdgd.pokemon.models_impl.repo.PokemonsRepository
import com.mdgd.pokemon.models_impl.repo.cache.PokemonsCacheImpl
import com.mdgd.pokemon.models_impl.repo.dao.PokemonsDaoImpl
import com.mdgd.pokemon.models_impl.repo.network.PokemonsNetwork
import com.mdgd.pokemon.models_impl.util.DispatchersHolderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DefaultAppModule {
    private val cache = CacheImpl()
    private val pokemonCache = PokemonsCacheImpl()

    @Provides
    @Singleton
    fun providePokemonsNetwork() = PokemonsNetwork() as Network

    @Provides
    @Singleton
    fun providePokemonsDao(@ApplicationContext ctx: Context) = PokemonsDaoImpl(ctx) as PokemonsDao

    @Provides
    @Singleton
    fun providePokemonsRepo(dao: PokemonsDao, network: Network) = PokemonsRepository(
        dao, network, pokemonCache
    ) as PokemonsRepo

    @Provides
    @Singleton
    fun provideCache() = cache as Cache

    @Provides
    @Singleton
    fun provideFiltersFactory() = StatsFiltersFactory() as StatsFilter

    @Provides
    @Singleton
    fun provideDispatchers() = DispatchersHolderImpl() as DispatchersHolder
}
