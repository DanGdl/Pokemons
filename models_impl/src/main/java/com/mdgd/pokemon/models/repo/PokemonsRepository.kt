package com.mdgd.pokemon.models.repo

import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.functions.Function

class PokemonsRepository(private val dao: PokemonsDao, private val network: Network) : PokemonsRepo {

    override fun getPage(page: Int): Single<Result<List<PokemonFullDataSchema>>> {
        return dao.getPage(page, PokemonsRepo.PAGE_SIZE)
                .flatMap(Function<Result<List<PokemonFullDataSchema>>, SingleSource<Result<List<PokemonFullDataSchema>>>> { result: Result<List<PokemonFullDataSchema>> ->
                    if (result.isError()) {
                        return@Function Single.just(Result<List<PokemonFullDataSchema>>(result.getError()))
                    } else {
                        val list = result.getValue()
                        return@Function if (list.isEmpty()) loadPage(page) else Single.just(Result(list))
                    }
                })
    }

    override fun loadPokemons(): Observable<Result<Long>> {
        val count = dao.getCount()
        return network.getPokemonsCount()
                .flatMapObservable { result: Result<Long> ->
                    if (result.isError()) {
                        return@flatMapObservable Observable.just(Result<Long>(result.getError()))
                    } else if (count == result.getValue()) {
                        return@flatMapObservable Observable.just(Result(count))
                    } else {
                        return@flatMapObservable loadPokemonsInner()
                    }
                }
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
