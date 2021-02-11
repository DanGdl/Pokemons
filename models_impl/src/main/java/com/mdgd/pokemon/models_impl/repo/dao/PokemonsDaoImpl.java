package com.mdgd.pokemon.models_impl.repo.dao;

import android.content.Context;

import androidx.room.Room;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class PokemonsDaoImpl implements PokemonsDao {
    private final PokemonsRoomDao pokemonsRoomDao;

    public PokemonsDaoImpl(Context ctx) {
        pokemonsRoomDao = Room.databaseBuilder(ctx, AppDatabase.class, "PokemonsAppDB").build().pokemonsDao();
    }

    @Override
    public Completable save(List<PokemonDetails> list) {
        return Completable.fromAction(() -> pokemonsRoomDao.save(list));
    }

    @Override
    public Single<Result<List<PokemonFullDataSchema>>> getPage(int page, int pageSize) {
        final int offset = page * pageSize;
        return Single.fromCallable(() -> {
            final int rows = pokemonsRoomDao.countRows();
            if (rows == 0) {
                return new Result<>(new ArrayList<>(0));
            } else if (pokemonsRoomDao.countRows() <= offset) {
                return new Result<>(new Exception("No more pokemons in DAO"));
            } else {
                return new Result<>(pokemonsRoomDao.getPage(offset, pageSize));
            }
        });
    }

    @Override
    public long getCount() {
        return pokemonsRoomDao.countRows();
    }

    @Override
    public Observable<Optional<PokemonFullDataSchema>> getPokemonById(Long id) {
        return Observable.just(pokemonsRoomDao.getPokemonById(id));
    }
}
