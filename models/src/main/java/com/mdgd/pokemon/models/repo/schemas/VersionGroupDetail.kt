package com.mdgd.pokemon.models.repo.schemas

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema

@Entity(
    tableName = "VersionGroupDetails", indices = [Index("id"), Index("moveId")],
    foreignKeys = [ForeignKey(
        entity = MoveSchema::class, parentColumns = ["id"],
        childColumns = ["moveId"], onDelete = ForeignKey.CASCADE
    )]
)
class VersionGroupDetail {

    var moveId: Long = 0

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("level_learned_at")
    var levelLearnedAt: Int? = null

    @Embedded
    @Expose
    @SerializedName("move_learn_method")
    var moveLearnMethod: MoveLearnMethod? = null

    @Embedded
    @Expose
    @SerializedName("version_group")
    var versionGroup: VersionGroup? = null
}
