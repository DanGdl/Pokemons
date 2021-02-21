package com.mdgd.pokemon.models_impl.repo.network

import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonsRetrofitApi {

    @GET("pokemon")
    suspend fun loadPage(@Query("limit") pageSize: Int, @Query("offset") offset: Int): PokemonsList

    @GET
    suspend fun getPokemonsDetails(@Url url: String?): PokemonDetails
}
