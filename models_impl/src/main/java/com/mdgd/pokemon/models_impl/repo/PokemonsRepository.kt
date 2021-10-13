package com.mdgd.pokemon.models_impl.repo

import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.cache.PokemonsCache
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.Network

class PokemonsRepository(private val dao: PokemonsDao, private val network: Network, private val cache: PokemonsCache) : PokemonsRepo {

    override fun getPokemons() = cache.getPokemons()

    override suspend fun getPage(page: Int): List<PokemonFullDataSchema> {
        val list = getPageFromDao(page)
        return if (list.isEmpty()) {
            loadPage(page)
        } else {
            list
        }
    }

    private suspend fun loadPage(page: Int): List<PokemonFullDataSchema> {
        dao.save(network.loadPokemons(page, PokemonsRepo.PAGE_SIZE))
        return getPageFromDao(page)
    }

    private suspend fun getPageFromDao(page: Int): List<PokemonFullDataSchema> {
        val list = dao.getPage(page, PokemonsRepo.PAGE_SIZE)
        if (list.isNotEmpty()) {
            if (page == 0) {
                cache.setPokemons(list)
            } else {
                cache.addPokemons(list)
            }
        }
        return list
    }

    override suspend fun loadPokemons(initialAmount: Long): Long {
        val count = dao.getCount()
        val pokemonsCount = network.getPokemonsCount()
        return when (count) {
            pokemonsCount -> count
            else -> loadPokemonsInner(pokemonsCount, initialAmount)
        }
    }

    private suspend fun loadPokemonsInner(pokemonsCount: Long, offset: Long): Long {
        val page = network.loadPokemons(pokemonsCount, offset)
        dao.save(page)
        return page.size.toLong()
    }

    override suspend fun getPokemonById(pokemonId: Long): PokemonFullDataSchema? {
        val pokemons = cache.getPokemons()
        for (p in pokemons) {
            if (pokemonId == p.pokemonSchema?.id) {
                return p
            }
        }
        return dao.getPokemonById(pokemonId)
    }

    override suspend fun loadInitialPages(amount: Long) {
        if (dao.getCount() < amount) {
            loadPokemonsInner(amount, 0)
        }
    }
}
