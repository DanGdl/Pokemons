package com.mdgd.pokemon.models.repo.schemas

import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Other {
    @Embedded
    @Expose
    @SerializedName("dream_world")
    var dreamWorld: DreamWorld? = null

    @Embedded
    @Expose
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtwork? = null
}
