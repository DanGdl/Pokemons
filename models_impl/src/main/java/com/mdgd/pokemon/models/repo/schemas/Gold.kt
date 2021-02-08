package com.mdgd.pokemon.models.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Gold {
    @Expose
    @SerializedName("back_default")
    var backDefault: String? = null

    @Expose
    @SerializedName("back_shiny")
    var backShiny: String? = null

    @Expose
    @SerializedName("front_default")
    var frontDefault: String? = null

    @Expose
    @SerializedName("front_shiny")
    var frontShiny: String? = null
}
