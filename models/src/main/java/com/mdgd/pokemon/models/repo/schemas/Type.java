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
        tableName = "types", indices = {@Index("id")},
        foreignKeys = {@ForeignKey(
                entity = PokemonSchema.class,
                parentColumns = "id",
                childColumns = "pokemonId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Type {

    public long pokemonId;
    @PrimaryKey(autoGenerate = true)
    private long id;
    @Expose
    @SerializedName("slot")
    private Integer slot;
    @Embedded
    @Expose
    @SerializedName("type")
    private Type_ type;

    public long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Type_ getType() {
        return type;
    }

    public void setType(Type_ type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
