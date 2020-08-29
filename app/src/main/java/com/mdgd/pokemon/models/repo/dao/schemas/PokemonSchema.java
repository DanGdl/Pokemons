package com.mdgd.pokemon.models.repo.dao.schemas;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.schemas.Species;
import com.mdgd.pokemon.models.repo.schemas.Sprites;

@Entity(tableName = "pokemons")
public class PokemonSchema {

    @Expose
    @SerializedName("base_experience")
    private Integer baseExperience;

    @Expose
    @SerializedName("height")
    private Integer height;

    @Expose
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("is_default")
    private Boolean isDefault;

    @Expose
    @SerializedName("location_area_encounters")
    private String locationAreaEncounters;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("order")
    private Integer order;

    @Embedded
    @Expose
    @SerializedName("species")
    private Species species;

    @Embedded
    @Expose
    @SerializedName("sprites")
    private Sprites sprites;

    @Expose
    @SerializedName("weight")
    private Integer weight;

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getLocationAreaEncounters() {
        return locationAreaEncounters;
    }

    public void setLocationAreaEncounters(String locationAreaEncounters) {
        this.locationAreaEncounters = locationAreaEncounters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
