package com.mdgd.pokemon.models.repo.schemas

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema

@Entity(
    tableName = "forms", indices = [Index("id"), Index("pokemonId")],
    foreignKeys = [ForeignKey(
        entity = PokemonSchema::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Form {
    var pokemonId: Long = 0

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("url")
    var url: String? = null
}
