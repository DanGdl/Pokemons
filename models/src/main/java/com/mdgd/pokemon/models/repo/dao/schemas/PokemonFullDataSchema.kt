package com.mdgd.pokemon.models.repo.dao.schemas

import androidx.room.Embedded
import androidx.room.Relation
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.schemas.*
import java.util.*

class PokemonFullDataSchema {
    @Expose
    @SerializedName("abilities")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Ability::class)
    var abilities: MutableList<Ability> = ArrayList()

    @Expose
    @SerializedName("forms")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Form::class)
    var forms: MutableList<Form> = ArrayList()

    @Expose
    @SerializedName("game_indices")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = GameIndex::class)
    var gameIndices: MutableList<GameIndex> = ArrayList()

    @Expose
    @SerializedName("moves")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Move::class)
    var moves: MutableList<MoveFullSchema> = ArrayList()

    @Expose
    @SerializedName("stats")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Stat::class)
    var stats: MutableList<Stat> = ArrayList()

    @Expose
    @SerializedName("types")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Type::class)
    var types: MutableList<Type> = ArrayList()

    @Embedded
    var pokemonSchema: PokemonSchema? = null
}
