package com.mdgd.pokemon.models.repo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mdgd.pokemon.models.repo.dao.schemas.MoveFullSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Move;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;
import com.mdgd.pokemon.models.repo.schemas.VersionGroupDetail;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class PokemonsRoomDao {

    @Transaction
    void save(List<PokemonDetails> pokemons) {
        if (countRows() == 0) {
            final List<PokemonSchema> schemas = new ArrayList<>();
            final List<Ability> abilities = new ArrayList<>();
            final List<GameIndex> gameIndexes = new ArrayList<>();
            final List<Form> forms = new ArrayList<>();
            final List<MoveSchema> moveSchemas = new ArrayList<>();
            final List<Move> moves = new ArrayList<>();
            final List<Type> types = new ArrayList<>();
            final List<Stat> stats = new ArrayList<>();

            for (PokemonDetails pd : pokemons) {
                PokemonSchema schema = new PokemonSchema();
                schema.setBaseExperience(pd.getBaseExperience());
                schema.setHeight(pd.getHeight());
                schema.setId(pd.getId());
                schema.setIsDefault(pd.getIsDefault());
                schema.setLocationAreaEncounters(pd.getLocationAreaEncounters());
                schema.setName(pd.getName());
                schema.setOrder(pd.getOrder());
                schema.setSpecies(pd.getSpecies());
                schema.setSprites(pd.getSprites());
                schema.setWeight(pd.getWeight());

                schemas.add(schema);

                for (Ability a : pd.getAbilities()) {
                    a.setPokemonId(schema.getId());
                }
                abilities.addAll(pd.getAbilities());

                for (GameIndex a : pd.getGameIndices()) {
                    a.setPokemonId(schema.getId());
                }
                gameIndexes.addAll(pd.getGameIndices());

                for (Form a : pd.getForms()) {
                    a.setPokemonId(schema.getId());
                }
                forms.addAll(pd.getForms());

                for (Move a : pd.getMoves()) {
                    final MoveSchema moveSchema = new MoveSchema();
                    moveSchema.setMove(a.getMove());
                    moveSchema.setPokemonId(schema.getId());
                    moveSchemas.add(moveSchema);
                }
                moves.addAll(pd.getMoves());

                for (Stat a : pd.getStats()) {
                    a.setPokemonId(schema.getId());
                }
                stats.addAll(pd.getStats());

                for (Type a : pd.getTypes()) {
                    a.setPokemonId(schema.getId());
                }
                types.addAll(pd.getTypes());
            }

            savePokemons(schemas);
            saveAbilities(abilities);
            saveGameIndexes(gameIndexes);
            saveForms(forms);
            final List<Long> ids = saveMoves(moveSchemas);
            final List<VersionGroupDetail> details = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                final Move move = moves.get(i);
                for (VersionGroupDetail a : move.getVersionGroupDetails()) {
                    a.setMoveId(ids.get(i));
                }
                details.addAll(move.getVersionGroupDetails());
            }
            saveVersionGroupDetails(details);
            saveTypes(types);
            saveStats(stats);
        }
    }

    @Insert
    protected abstract void saveAbilities(List<Ability> list);

    @Insert
    protected abstract void saveGameIndexes(List<GameIndex> list);

    @Insert
    protected abstract void saveForms(List<Form> list);

    @Insert
    protected abstract List<Long> saveMoves(List<MoveSchema> list);

    @Insert
    protected abstract void saveTypes(List<Type> list);

    @Insert
    protected abstract void saveStats(List<Stat> list);

    @Insert
    protected abstract void saveVersionGroupDetails(List<VersionGroupDetail> details);

    @Insert
    protected abstract void savePokemons(List<PokemonSchema> list);

    @Query("SELECT COUNT(*) FROM pokemons")
    public abstract int countRows();


    public List<PokemonFullDataSchema> getPage(int offset, int pageSize) {
        // todo use maps and indexes to improve speed
        final List<PokemonFullDataSchema> schemas = new ArrayList<>();
        final List<PokemonSchema> pokemons = getPokemonsForPage(offset, pageSize);
        for (PokemonSchema s : pokemons) {
            final PokemonFullDataSchema fullSchema = new PokemonFullDataSchema();
            schemas.add(fullSchema);

            fullSchema.setPokemonSchema(s);
            fullSchema.setAbilities(getPokemonAbilities(s.getId()));
            fullSchema.setForms(getPokemonForms(s.getId()));
            fullSchema.setGameIndices(getPokemonGameIndexes(s.getId()));
            fullSchema.setTypes(getPokemonTypes(s.getId()));
            fullSchema.setStats(getPokemonStats(s.getId()));

            final List<MoveFullSchema> moveSchemas = new ArrayList<>();
            final List<MoveSchema> pokemonMoves = getPokemonMoves(s.getId());
            for (MoveSchema ms : pokemonMoves) {
                final MoveFullSchema moveFullSchema = new MoveFullSchema();
                moveFullSchema.setMove(ms);
                moveFullSchema.setVersionGroupDetails(getVersionGroupDetails(ms.getId()));
                moveSchemas.add(moveFullSchema);
            }
            fullSchema.setMoves(moveSchemas);
        }
        return schemas;
    }

    @Query("SELECT * FROM versiongroupdetails WHERE moveId = :id")
    protected abstract List<VersionGroupDetail> getVersionGroupDetails(long id);

    @Query("SELECT * FROM moves WHERE pokemonId = :id")
    protected abstract List<MoveSchema> getPokemonMoves(Integer id);

    @Query("SELECT * FROM stats WHERE pokemonId = :id")
    protected abstract List<Stat> getPokemonStats(Integer id);

    @Query("SELECT * FROM types WHERE pokemonId = :id")
    protected abstract List<Type> getPokemonTypes(Integer id);

    @Query("SELECT * FROM game_indexes WHERE pokemonId = :id")
    protected abstract List<GameIndex> getPokemonGameIndexes(Integer id);

    @Query("SELECT * FROM forms WHERE pokemonId = :id")
    protected abstract List<Form> getPokemonForms(Integer id);

    @Query("SELECT * FROM abilities WHERE pokemonId = :id")
    protected abstract List<Ability> getPokemonAbilities(Integer id);

    @Query("SELECT * FROM pokemons LIMIT :pageSize OFFSET :offset")
    protected abstract List<PokemonSchema> getPokemonsForPage(int offset, int pageSize);
}
