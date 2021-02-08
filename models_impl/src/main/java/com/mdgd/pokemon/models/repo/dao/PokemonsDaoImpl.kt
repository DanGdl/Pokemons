package com.mdgd.pokemon.models.repo.dao

import android.content.Context
import androidx.room.Room
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*

class PokemonsDaoImpl(ctx: Context) : PokemonsDao {
    private val pokemonsRoomDao: PokemonsRoomDao? = Room.databaseBuilder(ctx, AppDatabase::class.java, "PokemonsAppDB").build().pokemonsDao()

    override fun save(list: List<PokemonDetails>): Completable {
        return Completable.fromAction { pokemonsRoomDao!!.save(list) }
    }

    override fun getPage(page: Int, pageSize: Int): Single<Result<List<PokemonFullDataSchema>>> {
        val offset = page * pageSize
        return Single.fromCallable {
            val rows = pokemonsRoomDao!!.countRows()
            when {
                rows == 0 -> {
                    return@fromCallable Result<List<PokemonFullDataSchema>>(ArrayList(0))
                }
                pokemonsRoomDao.countRows() <= offset -> {
                    return@fromCallable Result<List<PokemonFullDataSchema>>(Exception("No more pokemons in DAO"))
                }
                else -> {
                    return@fromCallable Result(pokemonsRoomDao.getPage(offset, pageSize))
                }
            }
        }
    }

    override fun getCount(): Long {
        return pokemonsRoomDao!!.countRows().toLong()
    }
}
