package com.mdgd.pokemon.models.repo.schemas

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sprites {

    @Expose
    @SerializedName("back_default")
    var backDefault: String? = null

    @Expose
    @SerializedName("back_female")
    var backFemale: String? = null

    @Expose
    @SerializedName("back_shiny")
    var backShiny: String? = null

    @Expose
    @SerializedName("back_shiny_female")
    var backShinyFemale: String? = null

    @ColumnInfo(name = "SpritesFrontDefault")
    @Expose
    @SerializedName("front_default")
    var frontDefault: String? = null

    @Expose
    @SerializedName("front_female")
    var frontFemale: String? = null

    @Expose
    @SerializedName("front_shiny")
    var frontShiny: String? = null

    @Expose
    @SerializedName("front_shiny_female")
    var frontShinyFemale: String? = null

    @Embedded
    @Expose
    @SerializedName("other")
    var other: Other? = null
}
