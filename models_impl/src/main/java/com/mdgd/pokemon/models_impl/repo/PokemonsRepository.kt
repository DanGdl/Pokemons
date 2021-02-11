package com.mdgd.pokemon.models_impl.repo

import com.google.common.base.Optional
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.cache.PokemonsCache
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.functions.Function

class PokemonsRepository(private val dao: PokemonsDao, private val network: Network, private val cache: PokemonsCache) : PokemonsRepo {

    override fun getPage(page: Int): Single<Result<List<PokemonFullDataSchema>>> {
        return dao.getPage(page, PokemonsRepo.PAGE_SIZE)
                .flatMap(Function<Result<List<PokemonFullDataSchema>>, SingleSource<Result<List<PokemonFullDataSchema>>>> { result: Result<List<PokemonFullDataSchema>> ->
                    if (result.isError()) {
                        return@Function Single.just(Result<List<PokemonFullDataSchema>>(result.getError()))
                    } else {
                        val list = result.getValue()
                        return@Function (if (list.isEmpty()) loadPage(page) else Single.just(Result(list)))
                                .doOnSuccess { event: Result<List<PokemonFullDataSchema>> ->
                                    if (page == 0) {
                                        cache.setPokemons(event.getValue())
                                    } else {
                                        cache.addPokemons(event.getValue())
                                    }
                                }
                    }
                })
    }

    override fun getPokemons() = cache.getPokemons()

    override fun getPokemonById(pokemonId: Long): Observable<Optional<PokemonFullDataSchema>> {
        val pokemons = cache.getPokemons()
        for (p in pokemons) {
            if (pokemonId == p.pokemonSchema?.id) {
                return Observable.just(Optional.fromNullable(p))
            }
        }
        return dao.getPokemonById(pokemonId)
    }

    private fun loadPage(page: Int): Single<Result<List<PokemonFullDataSchema>>> {
        return network.loadPokemons(page, PokemonsRepo.PAGE_SIZE)
                .flatMap(Function<Result<List<PokemonDetails>>, SingleSource<out Result<List<PokemonFullDataSchema>>>> { result: Result<List<PokemonDetails>> ->
                    if (result.isError()) {
                        return@Function Single.just(Result<List<PokemonFullDataSchema>>(result.getError()))
                    } else {
                        return@Function dao.save(result.getValue())
                                .andThen(dao.getPage(page, PokemonsRepo.PAGE_SIZE))
                    }
                })
    }


    override suspend fun getPage_S(page: Int): List<PokemonFullDataSchema> {
        val list = dao.getPage_S(page, PokemonsRepo.PAGE_SIZE)
        return if (list.isEmpty()) loadPage_S(page) else list
    }

    private suspend fun loadPage_S(page: Int): List<PokemonFullDataSchema> {
        val list = network.loadPokemons_S(page, PokemonsRepo.PAGE_SIZE)
        dao.save_S(list)
        return dao.getPage_S(page, PokemonsRepo.PAGE_SIZE)
    }

    override suspend fun loadPokemons_S(): Long {
        val count = dao.getCount_S()
        val pokemonsCount = network.getPokemonsCount_S()
        return when (count) {
            pokemonsCount -> {
                count
            }
            else -> {
                loadPokemonsInner_S()
            }
        }
    }

    private suspend fun loadPokemonsInner_S(): Long {
        val page = network.loadPokemons_S(PokemonsRepo.PAGE_SIZE)
        dao.save_S(page)
        return page.size.toLong()
    }

    override suspend fun getPokemonById_S(pokemonId: Long): Optional<PokemonFullDataSchema> {
        val pokemons = cache.getPokemons()
        for (p in pokemons) {
            if (pokemonId == p.pokemonSchema?.id) {
                return Optional.fromNullable(p)
            }
        }
        return dao.getPokemonById_S(pokemonId)
    }
}
