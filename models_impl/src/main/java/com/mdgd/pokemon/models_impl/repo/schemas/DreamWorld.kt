package com.mdgd.pokemon.models_impl.repo.schemas

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DreamWorld {
    @ColumnInfo(name = "DreamWorldFrontDefault")
    @Expose
    @SerializedName("front_default")
    var frontDefault: String? = null

    @ColumnInfo(name = "DreamWorldFrontFemale")
    @Expose
    @SerializedName("front_female")
    var frontFemale: String? = null
}
