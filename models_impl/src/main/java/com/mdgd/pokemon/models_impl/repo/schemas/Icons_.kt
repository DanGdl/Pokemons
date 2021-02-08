package com.mdgd.pokemon.models_impl.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Icons_ {
    @SerializedName("front_default")
    @Expose
    var frontDefault: String? = null

    @SerializedName("front_female")
    @Expose
    var frontFemale: String? = null
}
