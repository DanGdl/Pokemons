package com.mdgd.pokemon.models_impl.repo.schemas

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Move {
    @Expose
    @SerializedName("move")
    var move: Move_? = null

    @Expose
    @SerializedName("version_group_details")
    var versionGroupDetails: List<VersionGroupDetail> = ArrayList()
}
