package com.mdgd.pokemon.models_impl.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Ability_ {
    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("url")
    var url: String? = null
}
