package com.mdgd.pokemon.models.repo.dao.schemas;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Move;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;

import java.util.List;

public class PokemonFullDataSchema {
    @Expose
    @SerializedName("abilities")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Ability.class)
    private List<Ability> abilities;

    @Expose
    @SerializedName("forms")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Form.class)
    private List<Form> forms;

    @Expose
    @SerializedName("game_indices")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = GameIndex.class)
    private List<GameIndex> gameIndices;

    @Expose
    @SerializedName("moves")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Move.class)
    private List<MoveFullSchema> moves;
    ;

    @Expose
    @SerializedName("stats")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Stat.class)
    private List<Stat> stats;

    @Expose
    @SerializedName("types")
    @Relation(parentColumn = "id", entityColumn = "pokemonId", entity = Type.class)
    private List<Type> types;

    @Embedded
    private PokemonSchema pokemonSchema;

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
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

    public List<MoveFullSchema> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveFullSchema> moves) {
        this.moves = moves;
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

    public PokemonSchema getPokemonSchema() {
        return pokemonSchema;
    }

    public void setPokemonSchema(PokemonSchema pokemonSchema) {
        this.pokemonSchema = pokemonSchema;
    }
}
