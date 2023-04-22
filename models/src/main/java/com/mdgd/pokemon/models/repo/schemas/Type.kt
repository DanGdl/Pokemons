package com.mdgd.pokemon.models.repo.schemas

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema

@Entity(
    tableName = "types", indices = [Index("id"), Index("pokemonId")],
    foreignKeys = [ForeignKey(
        entity = PokemonSchema::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Type {
    var pokemonId: Long = 0

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("slot")
    var slot: Int? = null

    @Embedded
    @Expose
    @SerializedName("type")
    var type: Type_? = null
}
