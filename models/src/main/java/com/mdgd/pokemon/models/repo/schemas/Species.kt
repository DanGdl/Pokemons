package com.mdgd.pokemon.models.repo.schemas

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Species {
    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "species_name")
    var name: String? = null

    @Expose
    @SerializedName("url")
    @ColumnInfo(name = "species_url")
    var url: String? = null
}
