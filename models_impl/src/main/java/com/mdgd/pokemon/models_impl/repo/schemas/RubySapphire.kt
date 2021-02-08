package com.mdgd.pokemon.models_impl.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RubySapphire {
    @SerializedName("back_default")
    @Expose
    var backDefault: String? = null

    @SerializedName("back_shiny")
    @Expose
    var backShiny: String? = null

    @SerializedName("front_default")
    @Expose
    var frontDefault: String? = null

    @SerializedName("front_shiny")
    @Expose
    var frontShiny: String? = null
}
