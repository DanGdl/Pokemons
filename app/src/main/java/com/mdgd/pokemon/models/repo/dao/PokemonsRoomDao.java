package com.mdgd.pokemon.models.repo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

@Dao
public interface PokemonsRoomDao {

    @Insert()
    void save(List<PokemonDetails> pokemons);

    @Update()
    void update(List<PokemonDetails> pokemons);

    @Query("SELECT * FROM pokemons LIMIT :pageSize OFFSET :offset")
    List<PokemonDetails> getPage(int offset, int pageSize);

    @Query("SELECT COUNT(*) FROM pokemons")
    int countRows();
}
