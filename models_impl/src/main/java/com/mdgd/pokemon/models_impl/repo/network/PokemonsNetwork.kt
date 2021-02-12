package com.mdgd.pokemon.models_impl.repo.network

import com.mdgd.pokemon.models.BuildConfig
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.max

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

    override suspend fun loadPokemons(pokemonsCount: Long, offset: Long): List<PokemonDetails> {
        val nextPage = service.loadPage_S(pokemonsCount.toInt(), offset.toInt())
        return mapToDetails(nextPage)
    }

    override suspend fun loadPokemons(page: Int, pageSize: Int): List<PokemonDetails> {
        val i = max(page - 1, 0)
        val dataPage = service.loadPage_S(pageSize, i * pageSize)
        return mapToDetails(dataPage)
    }

    override suspend fun getPokemonsCount(): Long {
        return service.loadPage_S(1, 0).count.toLong()
    }

    private suspend fun mapToDetails(result: PokemonsList): List<PokemonDetails> {
        val list = if (result.results == null) {
            ArrayList()
        } else {
            result.results!!
        }
        val details = Vector<PokemonDetails>(list.size)
        val channel = Channel<PokemonDetails>()
        supervisorScope {
            for (item in list) {
                launch {
                    channel.send(service.getPokemonsDetails_S(item.url))
                }
            }

            launch {
                while (details.size != list.size) {
                    details.add(channel.receive())
                }
            }
        }
        return ArrayList(details)
    }
}
