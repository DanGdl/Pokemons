package com.mdgd.pokemon.models.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Icons {
    @SerializedName("front_default")
    @Expose
    var frontDefault: String? = null

    @SerializedName("front_female")
    @Expose
    var frontFemale: String? = null
}
