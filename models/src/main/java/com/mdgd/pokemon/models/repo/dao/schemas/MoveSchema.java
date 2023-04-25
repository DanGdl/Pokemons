package com.mdgd.pokemon.models.repo.dao.schemas;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.schemas.Move_;

@Entity(
        tableName = "moves", indices = {@Index("id"), @Index("pokemonId")},
        foreignKeys = {@ForeignKey(
                entity = PokemonSchema.class,
                parentColumns = "id",
                childColumns = "pokemonId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class MoveSchema {

    public long pokemonId;

    @PrimaryKey(autoGenerate = true)
    private long id;
    @Embedded
    @Expose
    @SerializedName("move")
    private Move_ move;

    public long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public Move_ getMove() {
        return move;
    }

    public void setMove(Move_ move) {
        this.move = move;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
