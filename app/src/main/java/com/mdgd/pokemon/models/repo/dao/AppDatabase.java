package com.mdgd.pokemon.models.repo.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

@Database(entities = {PokemonDetails.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonsRoomDao pokemonsDao();
}
