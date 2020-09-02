package com.mdgd.pokemon.models.repo.network;

import com.mdgd.pokemon.BuildConfig;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonData;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonsList;

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
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        httpClient.connectTimeout(10, TimeUnit.SECONDS);

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build();

        service = retrofit.create(PokemonsRetrofitApi.class);
    }

    @Override
    public Single<Result<List<PokemonDetails>>> loadPokemons(Integer page, int pageSize) {
        final int i = Math.max(page - 1, 0);
        return service.loadPage(pageSize, i * pageSize)
                .flatMap(result -> mapToDetails(result, pageSize)
                        .firstOrError())
                .map(Result::new)
                .onErrorReturn(Result::new);
    }

    /**
     * loads all pokemons
     */
    @Override
    public Observable<Result<List<PokemonDetails>>> loadPokemons(int bulkSize) {
        return service.loadPage(1, 0)
                .flatMap(list -> service.loadPage(list.getCount(), 0))
                .flatMapObservable(result -> mapToDetails(result, bulkSize))
                .map(Result::new)
                .onErrorReturn(Result::new);
    }

    @Override
    public Single<Result<Long>> getPokemonsCount() {
        return service.loadPage(1, 0)
                .map(list -> new Result<>((long) list.getCount()))
                .onErrorReturn(Result::new);
    }

    private Observable<List<PokemonDetails>> mapToDetails(PokemonsList result, int bulkSize) {
        final List<PokemonData> list = result.getResults();
        final List<List<PokemonData>> lists = new ArrayList<>();
        final int page = Math.min(bulkSize, 150);
        int start = 0;
        int end = Math.min(page, list.size());
        for (int i = 0; i < 3; i++) {
            lists.add(list.subList(start, end));
            start = end;
            end = Math.min(end + page, list.size());
            if (start == list.size()) {
                break;
            }
        }
        if (start != list.size()) {
            lists.add(list.subList(start, list.size()));
        }

        return Observable.fromIterable(lists)
                .flatMap(bulk -> Observable.fromIterable(bulk)
                        .flatMap(data -> service.getPokemonsDetails(data.getUrl())
                                .toObservable())
                        .collectInto(new ArrayList<PokemonDetails>(), ArrayList::add)
                        .toObservable()
                        .map(collected -> collected));
    }
}
