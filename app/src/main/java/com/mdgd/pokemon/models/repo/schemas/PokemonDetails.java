package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "pokemons")
public class PokemonDetails {

    @Embedded
    @Expose
    @SerializedName("abilities")
    private ArrayList<Ability> abilities = new ArrayList<>();

    @Expose
    @SerializedName("base_experience")
    private Integer baseExperience;

    @Embedded
    @Expose
    @SerializedName("forms")
    private ArrayList<Form> forms = null;

    @Embedded
    @Expose
    @SerializedName("game_indices")
    private ArrayList<GameIndex> gameIndices = new ArrayList<>();

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

    @Embedded
    @Expose
    @SerializedName("moves")
    private ArrayList<Move> moves = new ArrayList<>();

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

    @Embedded
    @Expose
    @SerializedName("stats")
    private ArrayList<Stat> stats = new ArrayList<>();

    @Embedded
    @Expose
    @SerializedName("types")
    private ArrayList<Type> types = new ArrayList<>();

    @Expose
    @SerializedName("weight")
    private Integer weight;

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public ArrayList<Form> getForms() {
        return forms;
    }

    public void setForms(ArrayList<Form> forms) {
        this.forms = forms;
    }

    public ArrayList<GameIndex> getGameIndices() {
        return gameIndices;
    }

    public void setGameIndices(ArrayList<GameIndex> gameIndices) {
        this.gameIndices = gameIndices;
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

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
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

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
