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
        tableName = "stats", indices = {@Index("id")},
        foreignKeys = {@ForeignKey(
                entity = PokemonSchema.class,
                parentColumns = "id",
                childColumns = "pokemonId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Stat {

    public long pokemonId;
    @PrimaryKey(autoGenerate = true)
    private long id;
    @Expose
    @SerializedName("base_stat")
    private Integer baseStat;
    @Expose
    @SerializedName("effort")
    private Integer effort;
    @Embedded
    @Expose
    @SerializedName("stat")
    private Stat_ stat;

    public long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public Integer getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(Integer baseStat) {
        this.baseStat = baseStat;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

    public Stat_ getStat() {
        return stat;
    }

    public void setStat(Stat_ stat) {
        this.stat = stat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
