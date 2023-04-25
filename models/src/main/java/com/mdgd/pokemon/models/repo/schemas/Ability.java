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
        tableName = "abilities", indices = {@Index("id"), @Index("pokemonId")},
        foreignKeys = {@ForeignKey(
                entity = PokemonSchema.class,
                parentColumns = "id",
                childColumns = "pokemonId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Ability {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public long pokemonId;

    @Embedded
    @Expose
    @SerializedName("ability")
    private Ability_ ability;

    @Expose
    @SerializedName("is_hidden")
    private Boolean isHidden;

    @Expose
    @SerializedName("slot")
    private Integer slot;

    public Ability_ getAbility() {
        return ability;
    }

    public void setAbility(Ability_ ability) {
        this.ability = ability;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(long pokemonId) {
        this.pokemonId = pokemonId;
    }
}
