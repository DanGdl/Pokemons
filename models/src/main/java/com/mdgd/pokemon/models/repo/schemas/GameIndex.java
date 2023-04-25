package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;

@Entity(
        tableName = "game_indexes", indices = {@Index("id"), @Index("pokemonId")},
        foreignKeys = {@ForeignKey(
                entity = PokemonSchema.class,
                parentColumns = "id",
                childColumns = "pokemonId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class GameIndex {

    public long pokemonId;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @Expose
    @SerializedName("game_index")
    private Integer gameIndex;

    @Embedded
    @Expose
    @SerializedName("version")
    private Version version;

    public Integer getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(Integer gameIndex) {
        this.gameIndex = gameIndex;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
