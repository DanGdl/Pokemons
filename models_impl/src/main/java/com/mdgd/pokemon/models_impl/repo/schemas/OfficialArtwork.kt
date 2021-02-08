package com.mdgd.pokemon.models_impl.repo.schemas

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OfficialArtwork {
    @ColumnInfo(name = "OfficialArtworkFrontDefault")
    @Expose
    @SerializedName("front_default")
    var frontDefault: String? = null
}
