package com.mdgd.pokemon.models_impl.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Crystal {
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
