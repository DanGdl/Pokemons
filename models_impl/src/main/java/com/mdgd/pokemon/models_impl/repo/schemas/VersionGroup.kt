package com.mdgd.pokemon.models_impl.repo.schemas

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VersionGroup {
    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "VersionGroupName")
    var name: String? = null

    @Expose
    @SerializedName("url")
    var url: String? = null
}
