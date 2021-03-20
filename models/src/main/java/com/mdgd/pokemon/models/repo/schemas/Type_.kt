package com.mdgd.pokemon.models.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Type_ {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}
