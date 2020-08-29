package com.mdgd.pokemon.models.repo.network;

import com.mdgd.pokemon.BuildConfig;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonsNetwork implements Network {

    private final PokemonsRetrofitApi service;

    public PokemonsNetwork() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        httpClient.connectTimeout(10, TimeUnit.SECONDS);

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build();

        service = retrofit.create(PokemonsRetrofitApi.class);
    }

    @Override
    public Single<Result<List<PokemonDetails>>> loadPokemons() {
        return service.loadPage(1, 0)
                .flatMap(list -> service.loadPage(60 /*list.getCount()*/, 0)) // todo restore when dao ready
                .flatMap(this::mapToDetails)
                .map(Result::new)
                .onErrorReturn(Result::new);
    }

    private Single<List<PokemonDetails>> mapToDetails(PokemonsList list) {
        return Observable.fromIterable(list.getResults())
                .flatMap(data -> service.getPokemonsDetails(data.getUrl())
                        .toObservable())
                .collectInto(new ArrayList<PokemonDetails>(), ArrayList::add)
                .map(collected -> collected);
    }
}
