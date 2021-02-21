package com.mdgd.pokemon

import com.google.gson.Gson
import com.mdgd.pokemon.models.repo.dao.schemas.MoveFullSchema
import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object Mocks {

    fun getPokemon(): PokemonFullDataSchema {
        val jsonFile = File(File(File("").absolutePath).parentFile.absolutePath + "/pokemon_details.json")
        val reader = BufferedReader(FileReader(jsonFile))
        val pokemonDetails = Gson().fromJson(reader, PokemonDetails::class.java)
        reader.close()

        val pokemon = PokemonFullDataSchema()
        pokemon.pokemonSchema = PokemonSchema()
        pokemon.pokemonSchema?.id = pokemonDetails.id?.toLong() ?: 0L
        pokemon.pokemonSchema?.baseExperience = pokemonDetails.baseExperience
        pokemon.pokemonSchema?.isDefault = pokemonDetails.isDefault
        pokemon.pokemonSchema?.locationAreaEncounters = pokemonDetails.locationAreaEncounters
        pokemon.pokemonSchema?.name = pokemonDetails.name
        pokemon.pokemonSchema?.order = pokemonDetails.order
        pokemon.pokemonSchema?.species = pokemonDetails.species
        pokemon.pokemonSchema?.sprites = pokemonDetails.sprites
        pokemon.pokemonSchema?.weight = pokemonDetails.weight
        pokemon.pokemonSchema?.height = pokemonDetails.height
        pokemon.abilities = ArrayList(pokemonDetails.abilities)
        pokemon.forms = ArrayList(pokemonDetails.forms)
        pokemon.gameIndices = ArrayList(pokemonDetails.gameIndices)
        pokemon.moves = ArrayList(pokemonDetails.moves.size)
        for (m in pokemonDetails.moves) {
            val move = MoveFullSchema()
            move.move = MoveSchema()
            move.move?.move = m.move
            move.versionGroupDetails = ArrayList(m.versionGroupDetails)
            pokemon.moves.add(move)
        }
        pokemon.stats = ArrayList(pokemonDetails.stats)
        pokemon.types = ArrayList(pokemonDetails.types)
        return pokemon
    }

    // TODO: impl later
    fun getPokemons(): PokemonFullDataSchema {
        val jsonFile = File(File(File("").absolutePath).parentFile.absolutePath + "/pokemon_details.json")
        val reader = BufferedReader(FileReader(jsonFile))
        val pokemon = Gson().fromJson(reader, PokemonFullDataSchema::class.java)
        reader.close()
        return pokemon
    }
}