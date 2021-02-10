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
import io.reactivex.rxjava3.core.ObservableSource
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

    override fun loadPokemons(): Observable<Result<Long>> {
        val count = dao.getCount()
        return network.getPokemonsCount()
                .flatMapObservable { result: Result<Long> ->
                    when {
                        result.isError() -> {
                            return@flatMapObservable Observable.just(Result<Long>(result.getError()))
                        }
                        count == result.getValue() -> {
                            return@flatMapObservable Observable.just(Result(count))
                        }
                        else -> {
                            return@flatMapObservable loadPokemonsInner()
                        }
                    }
                }
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

    private fun loadPokemonsInner(): Observable<Result<Long>> {
        return network.loadPokemons(PokemonsRepo.PAGE_SIZE * 3)
                .flatMap(Function<Result<List<PokemonDetails>>, ObservableSource<Result<Long>>> { result: Result<List<PokemonDetails>> ->
                    if (result.isError()) {
                        return@Function Observable.just(Result<Long>(result.getError()))
                    } else {
                        return@Function dao.save(result.getValue())
                                .toSingleDefault(Result(result.getValue().size.toLong()))
                                .toObservable()
                    }
                })
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
}
