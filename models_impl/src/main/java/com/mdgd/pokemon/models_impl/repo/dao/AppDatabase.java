package com.mdgd.pokemon.models_impl.repo.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;
import com.mdgd.pokemon.models.repo.schemas.VersionGroupDetail;

@Database(
        entities = {PokemonSchema.class, Ability.class, Form.class, GameIndex.class, MoveSchema.class,
                Stat.class, Type.class, VersionGroupDetail.class},
        version = 1, exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonsRoomDao pokemonsDao();
}
