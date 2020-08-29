package com.mdgd.pokemon.models.repo.network.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Move;
import com.mdgd.pokemon.models.repo.schemas.Species;
import com.mdgd.pokemon.models.repo.schemas.Sprites;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetails {

    @Expose
    @SerializedName("abilities")
    private List<Ability> abilities = new ArrayList<>();

    @Expose
    @SerializedName("base_experience")
    private Integer baseExperience;

    @Expose
    @SerializedName("forms")
    private List<Form> forms = new ArrayList<>();

    @Expose
    @SerializedName("game_indices")
    private List<GameIndex> gameIndices = new ArrayList<>();

    @Expose
    @SerializedName("height")
    private Integer height;

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("is_default")
    private Boolean isDefault;

    @Expose
    @SerializedName("location_area_encounters")
    private String locationAreaEncounters;

    @Expose
    @SerializedName("moves")
    private List<Move> moves = new ArrayList<>();
    ;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("order")
    private Integer order;

    @Expose
    @SerializedName("species")
    private Species species;

    @Expose
    @SerializedName("sprites")
    private Sprites sprites;

    @Expose
    @SerializedName("stats")
    private List<Stat> stats = new ArrayList<>();
    ;

    @Expose
    @SerializedName("types")
    private List<Type> types;

    @Expose
    @SerializedName("weight")
    private Integer weight;

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<GameIndex> getGameIndices() {
        return gameIndices;
    }

    public void setGameIndices(List<GameIndex> gameIndices) {
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

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
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

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
