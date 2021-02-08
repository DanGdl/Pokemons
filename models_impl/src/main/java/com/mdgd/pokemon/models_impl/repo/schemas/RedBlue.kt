package com.mdgd.pokemon.models_impl.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RedBlue {
    @Expose
    @SerializedName("back_default")
    var backDefault: String? = null

    @Expose
    @SerializedName("back_gray")
    var backGray: String? = null

    @Expose
    @SerializedName("front_default")
    var frontDefault: String? = null

    @Expose
    @SerializedName("front_gray")
    var frontGray: String? = null
}
