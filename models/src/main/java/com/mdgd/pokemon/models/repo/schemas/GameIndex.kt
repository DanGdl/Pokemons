package com.mdgd.pokemon.models.repo.schemas

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema

@Entity(
    tableName = "game_indexes", indices = [Index("id"), Index("pokemonId")],
    foreignKeys = [ForeignKey(
        entity = PokemonSchema::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class GameIndex {

    var pokemonId: Long = 0

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("game_index")
    var gameIndex: Int? = null

    @Embedded
    @Expose
    @SerializedName("version")
    var version: Version? = null
}
