package com.mdgd.pokemon.models_impl.repo.dao

import android.content.Context
import androidx.room.Room
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails

class PokemonsDaoImpl(ctx: Context) : PokemonsDao {
    private val pokemonsRoomDao: PokemonsRoomDao? = Room.databaseBuilder(
        ctx, AppDatabase::class.java, "PokemonsAppDB"
    ).build().pokemonsDao()

    override suspend fun save(list: List<PokemonDetails>) {
        pokemonsRoomDao?.save(list)
    }

    override suspend fun getPage(page: Int, pageSize: Int): List<PokemonFullDataSchema> {
        val offset = page * pageSize
        val rows = pokemonsRoomDao?.countRows() ?: 0
        return when {
            rows == 0 -> ArrayList(0)
            (pokemonsRoomDao?.countRows()
                ?: 0) <= offset -> throw Exception(PokemonsDao.NO_MORE_POKEMONS_MSG)

            else -> pokemonsRoomDao?.getPage(offset, pageSize) ?: listOf()
        }
    }

    override suspend fun getCount() = pokemonsRoomDao?.countRows()?.toLong() ?: 0

    override suspend fun getPokemonById(pokemonId: Long) =
        pokemonsRoomDao?.getPokemonById(pokemonId)
}
