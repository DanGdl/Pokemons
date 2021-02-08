package com.mdgd.pokemon.models_impl.repo.network.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models_impl.repo.schemas.*
import java.util.*

class PokemonDetails {
    @Expose
    @SerializedName("abilities")
    var abilities: List<Ability> = ArrayList()

    @Expose
    @SerializedName("base_experience")
    var baseExperience: Int? = null

    @Expose
    @SerializedName("forms")
    var forms: List<Form> = ArrayList()

    @Expose
    @SerializedName("game_indices")
    var gameIndices: List<GameIndex> = ArrayList()

    @Expose
    @SerializedName("height")
    var height: Int? = null

    @Expose
    @SerializedName("id")
    var id: Int? = null

    @Expose
    @SerializedName("is_default")
    var isDefault: Boolean? = null

    @Expose
    @SerializedName("location_area_encounters")
    var locationAreaEncounters: String? = null

    @Expose
    @SerializedName("moves")
    var moves: List<Move> = ArrayList()

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("order")
    var order: Int? = null

    @Expose
    @SerializedName("species")
    var species: Species? = null

    @Expose
    @SerializedName("sprites")
    var sprites: Sprites? = null

    @Expose
    @SerializedName("stats")
    var stats: List<Stat> = ArrayList()

    @Expose
    @SerializedName("types")
    var types: List<Type>? = null

    @Expose
    @SerializedName("weight")
    var weight: Int? = null
}
