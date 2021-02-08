package com.mdgd.pokemon.models.repo.dao.schemas

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.schemas.Species
import com.mdgd.pokemon.models.repo.schemas.Sprites

@Entity(tableName = "pokemons")
class PokemonSchema {
    @Expose
    @SerializedName("base_experience")
    var baseExperience: Int? = null

    @Expose
    @SerializedName("height")
    var height: Int? = null

    @Expose
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long = 0

    @Expose
    @SerializedName("is_default")
    var isDefault: Boolean? = null

    @Expose
    @SerializedName("location_area_encounters")
    var locationAreaEncounters: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("order")
    var order: Int? = null

    @Embedded
    @Expose
    @SerializedName("species")
    var species: Species? = null

    @Embedded
    @Expose
    @SerializedName("sprites")
    var sprites: Sprites? = null

    @Expose
    @SerializedName("weight")
    var weight: Int? = null
}
