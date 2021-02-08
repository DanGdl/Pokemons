package com.mdgd.pokemon.models_impl.repo.schemas

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mdgd.pokemon.models_impl.repo.dao.schemas.PokemonSchema

@Entity(tableName = "stats", indices = [Index("id")])
class Stat {
    @ForeignKey(entity = PokemonSchema::class, parentColumns = ["id"], childColumns = ["pokemonId"], onDelete = ForeignKey.CASCADE)
    var pokemonId: Long = 0

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("base_stat")
    var baseStat: Int? = null

    @Expose
    @SerializedName("effort")
    var effort: Int? = null

    @Embedded
    @Expose
    @SerializedName("stat")
    var stat: Stat_? = null
}