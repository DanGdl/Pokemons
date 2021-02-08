package com.mdgd.pokemon.models.repo.dao.schemas

import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.schemas.VersionGroupDetail
import java.util.*

class MoveFullSchema {
    @Embedded
    var move: MoveSchema? = null

    @Expose
    @SerializedName("version_group_details")
    var versionGroupDetails: MutableList<VersionGroupDetail?> = ArrayList()
}
