package com.mdgd.pokemon.models.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class XY {
    @SerializedName("front_default")
    @Expose
    var frontDefault: String? = null

    @SerializedName("front_female")
    @Expose
    var frontFemale: String? = null

    @SerializedName("front_shiny")
    @Expose
    var frontShiny: String? = null

    @SerializedName("front_shiny_female")
    @Expose
    var frontShinyFemale: String? = null
}
