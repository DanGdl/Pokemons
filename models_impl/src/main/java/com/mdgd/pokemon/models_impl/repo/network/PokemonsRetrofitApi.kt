package com.mdgd.pokemon.models_impl.repo.network

import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonsRetrofitApi {

    @GET("pokemon")
    fun loadPage(@Query("limit") pageSize: Int, @Query("offset") offset: Int): Single<PokemonsList>

    @GET
    fun getPokemonsDetails(@Url url: String?): Single<PokemonDetails>


    @GET("pokemon")
    suspend fun loadPage_S(@Query("limit") pageSize: Int, @Query("offset") offset: Int): PokemonsList

    @GET
    suspend fun getPokemonsDetails_S(@Url url: String?): PokemonDetails
}
