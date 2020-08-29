package com.mdgd.pokemon.models.repo.dao;

import android.content.Context;

import androidx.room.Room;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class PokemonsDaoImpl implements PokemonsDao {
    private final PokemonsRoomDao pokemonsRoomDao;

    public PokemonsDaoImpl(Context ctx) {
        pokemonsRoomDao = Room.databaseBuilder(ctx, AppDatabase.class, "PokemonsAppDB").build().pokemonsDao();
    }

    @Override
    public Completable save(List<PokemonDetails> list) {
        return Completable.fromAction(() -> {
            if (pokemonsRoomDao.countRows() == 0) {
                pokemonsRoomDao.save(list);
            } else {
                pokemonsRoomDao.update(list);
            }
        });
    }

    @Override
    public Single<Result<List<PokemonDetails>>> getPage(int page, int pageSize) {
        final int offset = page * pageSize;
        return Single.fromCallable(() -> {
            if (pokemonsRoomDao.countRows() <= offset) {
                return new Result<>(new Exception("No more pokemons in DAO"));
            } else {
                return new Result<>(pokemonsRoomDao.getPage(offset, pageSize));
            }
        });
    }
}
