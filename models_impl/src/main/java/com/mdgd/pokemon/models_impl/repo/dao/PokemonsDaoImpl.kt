package com.mdgd.pokemon.models_impl.repo.dao

import android.content.Context
import androidx.room.Room
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class PokemonsDaoImpl @Inject constructor(@ApplicationContext ctx: Context) : PokemonsDao {
    private val pokemonsRoomDao: PokemonsRoomDao? =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "PokemonsAppDB").build().pokemonsDao()

    override suspend fun save(list: List<PokemonDetails>) {
        pokemonsRoomDao?.save(list)
    }

    override suspend fun getPage(page: Int, pageSize: Int): List<PokemonFullDataSchema> {
        val offset = page * pageSize
        val rows = pokemonsRoomDao!!.countRows()
        return when {
            rows == 0 -> ArrayList(0)
            (pokemonsRoomDao.countRows() <= offset) -> throw Exception(PokemonsDao.NO_MORE_POKEMONS_MSG)
            else -> pokemonsRoomDao.getPage(offset, pageSize)
        }
    }

    override suspend fun getCount(): Long {
        return pokemonsRoomDao!!.countRows().toLong()
    }

    override suspend fun getPokemonById(pokemonId: Long): PokemonFullDataSchema? {
        return pokemonsRoomDao?.getPokemonById(pokemonId)
    }
}
