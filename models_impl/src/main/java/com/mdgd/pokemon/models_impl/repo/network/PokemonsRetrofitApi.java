package com.mdgd.pokemon.models_impl.repo.network;

import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokemonsRetrofitApi {

    @GET("pokemon")
    Single<PokemonsList> loadPage(@Query("limit") int pageSize, @Query("offset") int offset);

    @GET
    Single<PokemonDetails> getPokemonsDetails(@Url String url);
}
