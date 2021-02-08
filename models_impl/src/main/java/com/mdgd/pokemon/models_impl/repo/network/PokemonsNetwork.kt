package com.mdgd.pokemon.models_impl.repo.network

import com.mdgd.pokemon.models.BuildConfig
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.repo.network.schemas.PokemonData
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class PokemonsNetwork : Network {
    private val service: PokemonsRetrofitApi

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()
        service = retrofit.create(PokemonsRetrofitApi::class.java)
    }

    override fun loadPokemons(page: Int, pageSize: Int): Single<Result<List<PokemonDetails>>> {
        val i = Math.max(page - 1, 0)
        return service.loadPage(pageSize, i * pageSize)
                .flatMap { result: PokemonsList? ->
                    mapToDetails(result, pageSize).firstOrError()
                }
                .map { value: List<PokemonDetails> -> Result(value) }
                .onErrorReturn { error: Throwable -> Result(error) }
    }

    /**
     * loads all pokemons
     */
    override fun loadPokemons(bulkSize: Int): Observable<Result<List<PokemonDetails>>> {
        return service.loadPage(1, 0)
                .flatMap { list: PokemonsList -> service.loadPage(list.count, 0) }
                .flatMapObservable { result: PokemonsList? -> mapToDetails(result, bulkSize) }
                .map { value: List<PokemonDetails> -> Result(value) }
                .onErrorReturn { error: Throwable -> Result(error) }
    }

    override fun getPokemonsCount(): Single<Result<Long>> {
        return service.loadPage(1, 0)
                .map { list: PokemonsList -> Result(list.count.toLong()) }
                .onErrorReturn { error: Throwable -> Result(error) }
    }

    private fun mapToDetails(result: PokemonsList?, bulkSize: Int): Observable<List<PokemonDetails>> {
        val list = if (result?.results == null) {
            ArrayList()
        } else {
            result.results!!
        }
        val lists: MutableList<List<PokemonData>> = ArrayList()
        val page = Math.min(bulkSize, 150)
        var start = 0
        var end = Math.min(page, list.size)
        for (i in 0..2) {
            lists.add(list.subList(start, end))
            start = end
            end = Math.min(end + page, list.size)
            if (start == list.size) {
                break
            }
        }
        if (start != list.size) {
            lists.add(list.subList(start, list.size))
        }
        return Observable.fromIterable(lists)
                .flatMap { bulk: List<PokemonData> ->
                    Observable.fromIterable(bulk)
                            .flatMap { data: PokemonData ->
                                service.getPokemonsDetails(data.url)
                                        .toObservable()
                            }
                            .collectInto(ArrayList(), { obj: ArrayList<PokemonDetails>, e: PokemonDetails -> obj.add(e) })
                            .toObservable()
                            .map { collected: ArrayList<PokemonDetails> -> collected }
                }
    }
}