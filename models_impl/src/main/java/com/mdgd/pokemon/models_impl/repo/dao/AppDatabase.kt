package com.mdgd.pokemon.models_impl.repo.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.schemas.*

@Database(
    version = 1, exportSchema = false,
    entities = [
        PokemonSchema::class, Ability::class, Form::class, GameIndex::class,
        MoveSchema::class, Stat::class, Type::class, VersionGroupDetail::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonsDao(): PokemonsRoomDao?
}
