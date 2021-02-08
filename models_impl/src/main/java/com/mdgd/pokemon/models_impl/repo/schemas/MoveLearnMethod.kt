package com.mdgd.pokemon.models_impl.repo.schemas

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoveLearnMethod {
    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("url")
    @ColumnInfo(name = "MoveLearnMethodUrl")
    var url: String? = null
}
