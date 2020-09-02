package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "forms", indices = {@Index("id")})
public class Form {

    @ForeignKey(entity = PokemonSchema.class, parentColumns = "id", childColumns = "pokemonId", onDelete = CASCADE)
    public long pokemonId;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
