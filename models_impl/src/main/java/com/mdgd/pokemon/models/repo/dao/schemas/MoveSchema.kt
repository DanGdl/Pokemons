package com.mdgd.pokemon.models.repo.dao.schemas

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.schemas.Move_

@Entity(tableName = "moves", indices = [Index("id")])
class MoveSchema {
    @ForeignKey(entity = PokemonSchema::class, parentColumns = ["id"], childColumns = ["pokemonId"], onDelete = ForeignKey.CASCADE)
    var pokemonId: Long = 0

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Embedded
    @Expose
    @SerializedName("move")
    var move: Move_? = null
}
