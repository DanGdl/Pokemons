package com.mdgd.pokemon.models.repo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveAbilities(List<Ability> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveGameIndexes(List<GameIndex> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveForms(List<Form> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract List<Long> saveMoves(List<MoveSchema> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveTypes(List<Type> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveStats(List<Stat> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveVersionGroupDetails(List<VersionGroupDetail> details);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void savePokemons(List<PokemonSchema> list);

    @Query("SELECT COUNT(*) FROM pokemons")
    public abstract int countRows();


    public List<PokemonFullDataSchema> getPage(int offset, int pageSize) {
        final Map<Long, PokemonFullDataSchema> pokemonsMap = new LinkedHashMap<>();
        final List<PokemonSchema> pokemons = getPokemonsForPage(offset, pageSize);

        for (PokemonSchema s : pokemons) {
            final PokemonFullDataSchema fullSchema = new PokemonFullDataSchema();
            fullSchema.setPokemonSchema(s);
            pokemonsMap.put(s.getId(), fullSchema);
        }

        final List<Ability> abilities = getPokemonAbilities(pokemonsMap.keySet());
        final List<Form> forms = getPokemonForms(pokemonsMap.keySet());
        final List<GameIndex> gameIndexes = getPokemonGameIndexes(pokemonsMap.keySet());
        final List<Type> type = getPokemonTypes(pokemonsMap.keySet());
        final List<Stat> stats = getPokemonStats(pokemonsMap.keySet());

        final Map<Long, MoveFullSchema> movesMap = new HashMap<>();
        final List<MoveSchema> moves = getPokemonMoves(pokemonsMap.keySet());
        for (MoveSchema s : moves) {
            final MoveFullSchema fullSchema = new MoveFullSchema();
            fullSchema.setMove(s);
            movesMap.put(s.getId(), fullSchema);
        }
        final List<VersionGroupDetail> versionGroupDetails = new ArrayList<>();
        final List<Long> moveIds = new ArrayList<>(movesMap.keySet());
        final int page = 250;
        int startIdx = 0;
        int endIdx = page;
        while (true) {
            endIdx = Math.min(endIdx, moveIds.size());
            versionGroupDetails.addAll(getVersionGroupDetails(moveIds.subList(startIdx, endIdx)));
            startIdx = endIdx;
            endIdx = Math.min(startIdx + page, moveIds.size());
            if (startIdx >= moveIds.size()) {
                break;
            }
        }

        for (VersionGroupDetail a : versionGroupDetails) {
            movesMap.get(a.getMoveId()).getVersionGroupDetails().add(a);
        }


        for (Ability a : abilities) {
            pokemonsMap.get(a.getPokemonId()).getAbilities().add(a);
        }
        for (Form a : forms) {
            pokemonsMap.get(a.getPokemonId()).getForms().add(a);
        }
        for (GameIndex a : gameIndexes) {
            pokemonsMap.get(a.getPokemonId()).getGameIndices().add(a);
        }
        for (Type a : type) {
            pokemonsMap.get(a.getPokemonId()).getTypes().add(a);
        }
        for (Stat a : stats) {
            pokemonsMap.get(a.getPokemonId()).getStats().add(a);
        }
        for (MoveFullSchema a : movesMap.values()) {
            pokemonsMap.get(a.getMove().getPokemonId()).getMoves().add(a);
        }

        final List<PokemonFullDataSchema> schemas = new ArrayList<>(pokemonsMap.values());
        Collections.shuffle(schemas);
        return schemas;
    }

    @Query("SELECT * FROM versiongroupdetails WHERE moveId IN (:ids)")
    protected abstract List<VersionGroupDetail> getVersionGroupDetails(Collection<Long> ids);

    @Query("SELECT * FROM moves WHERE pokemonId IN (:ids)")
    protected abstract List<MoveSchema> getPokemonMoves(Collection<Long> ids);

    @Query("SELECT * FROM stats WHERE pokemonId IN (:ids)")
    protected abstract List<Stat> getPokemonStats(Collection<Long> ids);

    @Query("SELECT * FROM types WHERE pokemonId IN (:ids)")
    protected abstract List<Type> getPokemonTypes(Collection<Long> ids);

    @Query("SELECT * FROM game_indexes WHERE pokemonId IN (:ids)")
    protected abstract List<GameIndex> getPokemonGameIndexes(Collection<Long> ids);

    @Query("SELECT * FROM forms WHERE pokemonId IN (:ids)")
    protected abstract List<Form> getPokemonForms(Collection<Long> ids);

    @Query("SELECT * FROM abilities WHERE pokemonId IN (:ids)")
    protected abstract List<Ability> getPokemonAbilities(Collection<Long> ids);

    @Query("SELECT * FROM pokemons LIMIT :pageSize OFFSET :offset")
    protected abstract List<PokemonSchema> getPokemonsForPage(int offset, int pageSize);
}
