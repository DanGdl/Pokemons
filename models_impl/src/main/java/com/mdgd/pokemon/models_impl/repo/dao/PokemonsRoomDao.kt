package com.mdgd.pokemon.models_impl.repo.dao

import androidx.room.*
import com.google.common.base.Optional
import com.mdgd.pokemon.models.repo.dao.schemas.MoveFullSchema
import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import com.mdgd.pokemon.models.repo.schemas.*
import java.util.*
import kotlin.collections.ArrayList

@Dao
abstract class PokemonsRoomDao {

    @Transaction
    open fun save(pokemons: List<PokemonDetails>) {
        val schemas: MutableList<PokemonSchema> = ArrayList()
        val abilities: MutableList<Ability> = ArrayList()
        val gameIndexes: MutableList<GameIndex> = ArrayList()
        val forms: MutableList<Form> = ArrayList()
        val moveSchemas: MutableList<MoveSchema> = ArrayList()
        val moves: MutableList<Move> = ArrayList()
        val types: MutableList<Type> = ArrayList()
        val stats: MutableList<Stat> = ArrayList()
        for (pd in pokemons) {
            val schema = PokemonSchema()
            schema.baseExperience = pd.baseExperience
            schema.height = pd.height
            schema.id = pd.id!!.toLong()
            schema.isDefault = pd.isDefault
            schema.locationAreaEncounters = pd.locationAreaEncounters
            schema.name = pd.name
            schema.order = pd.order
            schema.species = pd.species
            schema.sprites = pd.sprites
            schema.weight = pd.weight
            schemas.add(schema)
            for (a in pd.abilities) {
                a.pokemonId = schema.id
            }
            abilities.addAll(pd.abilities)
            for (a in pd.gameIndices) {
                a.pokemonId = schema.id
            }
            gameIndexes.addAll(pd.gameIndices)
            for (a in pd.forms) {
                a.pokemonId = schema.id
            }
            forms.addAll(pd.forms)
            for (a in pd.moves) {
                val moveSchema = MoveSchema()
                moveSchema.move = a.move
                moveSchema.pokemonId = schema.id
                moveSchemas.add(moveSchema)
            }
            moves.addAll(pd.moves)
            for (a in pd.stats) {
                a.pokemonId = schema.id
            }
            stats.addAll(pd.stats)
            for (a in pd.types!!) {
                a.pokemonId = schema.id
            }
            types.addAll(pd.types!!)
        }
        savePokemons(schemas)
        saveAbilities(abilities)
        saveGameIndexes(gameIndexes)
        saveForms(forms)
        val ids = saveMoves(moveSchemas)
        val details: MutableList<VersionGroupDetail> = ArrayList()
        for (i in ids.indices) {
            val move = moves[i]
            for (a in move.versionGroupDetails) {
                a.moveId = ids[i]
            }
            details.addAll(move.versionGroupDetails)
        }
        saveVersionGroupDetails(details)
        saveTypes(types)
        saveStats(stats)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveAbilities(list: List<Ability>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveGameIndexes(list: List<GameIndex>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveForms(list: List<Form>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveMoves(list: List<MoveSchema>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveTypes(list: List<Type>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveStats(list: List<Stat>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun saveVersionGroupDetails(details: List<VersionGroupDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun savePokemons(list: List<PokemonSchema>)

    @Query("SELECT COUNT(*) FROM pokemons")
    abstract fun countRows(): Int

    fun getPage(offset: Int, pageSize: Int): List<PokemonFullDataSchema> {
        val pokemons = getPokemonsForPage(offset, pageSize)
        val schemas: List<PokemonFullDataSchema> = ArrayList(mapPokemons(pokemons))
        Collections.shuffle(schemas)
        return schemas
    }

    private fun mapPokemons(pokemons: List<PokemonSchema>): MutableCollection<PokemonFullDataSchema> {
        val pokemonsMap: MutableMap<Long, PokemonFullDataSchema> = LinkedHashMap()
        for (s in pokemons) {
            val fullSchema = PokemonFullDataSchema()
            fullSchema.pokemonSchema = s
            pokemonsMap[s.id] = fullSchema
        }
        val abilities = getPokemonAbilities(pokemonsMap.keys)
        val forms = getPokemonForms(pokemonsMap.keys)
        val gameIndexes = getPokemonGameIndexes(pokemonsMap.keys)
        val type = getPokemonTypes(pokemonsMap.keys)
        val stats = getPokemonStats(pokemonsMap.keys)
        val movesMap: MutableMap<Long, MoveFullSchema> = HashMap()
        val moves = getPokemonMoves(pokemonsMap.keys)
        for (s in moves) {
            val fullSchema = MoveFullSchema()
            fullSchema.move = s
            movesMap[s.id] = fullSchema
        }
        val versionGroupDetails: MutableList<VersionGroupDetail> = ArrayList()
        val moveIds: List<Long> = ArrayList(movesMap.keys)
        val page = 250
        var startIdx = 0
        var endIdx = page
        while (true) {
            endIdx = Math.min(endIdx, moveIds.size)
            versionGroupDetails.addAll(getVersionGroupDetails(moveIds.subList(startIdx, endIdx)))
            startIdx = endIdx
            endIdx = Math.min(startIdx + page, moveIds.size)
            if (startIdx >= moveIds.size) {
                break
            }
        }
        for (a in versionGroupDetails) {
            movesMap[a.moveId]?.versionGroupDetails?.add(a)
        }
        for (a in abilities) {
            pokemonsMap[a.pokemonId]?.abilities?.add(a)
        }
        for (a in forms) {
            pokemonsMap[a.pokemonId]?.forms?.add(a)
        }
        for (a in gameIndexes) {
            pokemonsMap[a.pokemonId]?.gameIndices?.add(a)
        }
        for (a in type) {
            pokemonsMap[a.pokemonId]?.types?.add(a)
        }
        for (a in stats) {
            pokemonsMap[a.pokemonId]?.stats?.add(a)
        }
        for (a in movesMap.values) {
            pokemonsMap[a.move?.pokemonId]?.moves?.add(a)
        }
        return pokemonsMap.values
    }

    @Query("SELECT * FROM versiongroupdetails WHERE moveId IN (:ids)")
    protected abstract fun getVersionGroupDetails(ids: Collection<Long>?): List<VersionGroupDetail>

    @Query("SELECT * FROM moves WHERE pokemonId IN (:ids)")
    protected abstract fun getPokemonMoves(ids: Collection<Long>?): List<MoveSchema>

    @Query("SELECT * FROM stats WHERE pokemonId IN (:ids)")
    protected abstract fun getPokemonStats(ids: Collection<Long>?): List<Stat>

    @Query("SELECT * FROM types WHERE pokemonId IN (:ids)")
    protected abstract fun getPokemonTypes(ids: Collection<Long>?): List<Type>

    @Query("SELECT * FROM game_indexes WHERE pokemonId IN (:ids)")
    protected abstract fun getPokemonGameIndexes(ids: Collection<Long>?): List<GameIndex>

    @Query("SELECT * FROM forms WHERE pokemonId IN (:ids)")
    protected abstract fun getPokemonForms(ids: Collection<Long>?): List<Form>

    @Query("SELECT * FROM abilities WHERE pokemonId IN (:ids)")
    protected abstract fun getPokemonAbilities(ids: Collection<Long>?): List<Ability>

    @Query("SELECT * FROM pokemons LIMIT :pageSize OFFSET :offset")
    protected abstract fun getPokemonsForPage(offset: Int, pageSize: Int): List<PokemonSchema>

    @Query("SELECT * FROM pokemons WHERE id in (:ids)")
    protected abstract fun getPokemonsById(ids: List<Long>): List<PokemonSchema>

    fun getPokemonById(pokemonId: Long): Optional<PokemonFullDataSchema> {
        val pokemonsById = getPokemonsById(listOf(pokemonId))
        return if (pokemonsById.isEmpty()) {
            Optional.absent()
        } else {
            Optional.fromNullable(mapPokemons(pokemonsById).firstOrNull())
        }
    }
}
