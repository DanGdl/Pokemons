package com.mdgd.pokemon.models.repo.network

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
}
