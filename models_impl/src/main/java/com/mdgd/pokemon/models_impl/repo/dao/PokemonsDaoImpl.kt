package com.mdgd.pokemon.models_impl.repo.dao

import android.content.Context
import androidx.room.Room
import com.google.common.base.Optional
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
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
                    return@fromCallable Result<List<PokemonFullDataSchema>>(Exception(PokemonsDao.NO_MORE_POKEMONS_MSG))
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

    override fun getPokemonById(pokemonId: Long): Observable<Optional<PokemonFullDataSchema>> {
        return Observable.just(pokemonsRoomDao!!.getPokemonById(pokemonId))
    }


    override suspend fun save_S(list: List<PokemonDetails>) {
        pokemonsRoomDao!!.save(list)
    }

    override suspend fun getPage_S(page: Int, pageSize: Int): List<PokemonFullDataSchema> {
        val offset = page * pageSize
        val rows = pokemonsRoomDao!!.countRows()
        return when {
            rows == 0 -> {
                ArrayList(0)
            }
            pokemonsRoomDao.countRows() <= offset -> {
                throw Exception(PokemonsDao.NO_MORE_POKEMONS_MSG)
            }
            else -> {
                pokemonsRoomDao.getPage(offset, pageSize)
            }
        }
    }

    override suspend fun getCount_S(): Long {
        return pokemonsRoomDao!!.countRows().toLong()
    }

    override suspend fun getPokemonById_S(pokemonId: Long): Optional<PokemonFullDataSchema> {
        return pokemonsRoomDao!!.getPokemonById(pokemonId)
    }
}
