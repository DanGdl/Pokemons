package com.mdgd.pokemon.models.repo.schemas

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema

@Entity(
    tableName = "abilities", indices = [Index("id"), Index("pokemonId")],
    foreignKeys = [ForeignKey(
        entity = PokemonSchema::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Ability {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var pokemonId: Long = 0

    @Embedded
    @Expose
    @SerializedName("ability")
    var ability: Ability_? = null

    @Expose
    @SerializedName("is_hidden")
    var isHidden: Boolean? = null

    @Expose
    @SerializedName("slot")
    var slot: Int? = null
}
