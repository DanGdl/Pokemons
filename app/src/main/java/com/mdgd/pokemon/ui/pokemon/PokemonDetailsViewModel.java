package com.mdgd.pokemon.ui.pokemon;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.dao.schemas.MoveFullSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Species;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;
import com.mdgd.pokemon.models.repo.schemas.VersionGroupDetail;
import com.mdgd.pokemon.ui.arch.MviViewModel;
import com.mdgd.pokemon.ui.pokemon.infra.ImagePropertyData;
import com.mdgd.pokemon.ui.pokemon.infra.LabelImagePropertyData;
import com.mdgd.pokemon.ui.pokemon.infra.LabelPropertyData;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;
import com.mdgd.pokemon.ui.pokemon.infra.TitlePropertyData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PokemonDetailsViewModel extends MviViewModel<PokemonDetailsScreenState> implements PokemonDetailsContract.ViewModel {

    private final Cache cache;

    public PokemonDetailsViewModel(Cache cache) {
        this.cache = cache;
    }

    @Override
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(cache.getSelectedPokemonObservable()
                    .map(optional -> optional.isPresent() ? mapToListPokemon(optional.get()) : new LinkedList<PokemonProperty>())
                    .subscribe(list -> setState(PokemonDetailsScreenState.createSetDataState(list))));
        }
    }

    private List<PokemonProperty> mapToListPokemon(PokemonFullDataSchema pokemonDetails) {
        final List<PokemonProperty> properties = new ArrayList<>();
        final PokemonSchema pokemonSchema = pokemonDetails.getPokemonSchema();
        properties.add(new ImagePropertyData(pokemonSchema.getSprites().getOther().getOfficialArtwork().getFrontDefault()));
        properties.add(new LabelPropertyData(R.string.pokemon_detail_name, pokemonSchema.getName()));
        properties.add(new LabelPropertyData(R.string.pokemon_detail_height, String.valueOf(pokemonSchema.getHeight())));
        properties.add(new LabelPropertyData(R.string.pokemon_detail_weight, String.valueOf(pokemonSchema.getWeight())));
        final Species species = pokemonDetails.getPokemonSchema().getSpecies();
        properties.add(new LabelImagePropertyData(species.getName(), species.getUrl()));
        properties.add(new TitlePropertyData(R.string.pokemon_detail_stats));
        for (Stat s : pokemonDetails.getStats()) {
            properties.add(new LabelImagePropertyData(s.getStat().getName(), s.getStat().getUrl()));
        }
        properties.add(new TitlePropertyData(R.string.pokemon_detail_abilities));
        for (Ability a : pokemonDetails.getAbilities()) {
            properties.add(new LabelImagePropertyData(a.getAbility().getName(), a.getAbility().getUrl(), 1));
        }
        properties.add(new TitlePropertyData(R.string.pokemon_detail_forms));
        for (Form f : pokemonDetails.getForms()) {
            properties.add(new LabelImagePropertyData(f.getName(), f.getUrl(), 1));
        }
        properties.add(new TitlePropertyData(R.string.pokemon_detail_types));
        for (Type t : pokemonDetails.getTypes()) {
            properties.add(new LabelImagePropertyData(t.getType().getName(), t.getType().getUrl(), 1));
        }
        properties.add(new TitlePropertyData(R.string.pokemon_detail_game_indicies));
        for (GameIndex gi : pokemonDetails.getGameIndices()) {
            properties.add(new LabelImagePropertyData(gi.getVersion().getName(), gi.getVersion().getUrl(), 1));
        }
        properties.add(new TitlePropertyData(R.string.pokemon_detail_game_moves));
        for (MoveFullSchema m : pokemonDetails.getMoves()) {
            properties.add(new LabelImagePropertyData(m.getMove().getMove().getName(), m.getMove().getMove().getUrl(), 1));

            properties.add(new TitlePropertyData(R.string.pokemon_detail_version_group_details, 1));
            for (VersionGroupDetail vgd : m.getVersionGroupDetails()) {
                properties.add(new LabelImagePropertyData(vgd.getVersionGroup().getName(), vgd.getVersionGroup().getUrl(), 2));
            }
        }
        return properties;
    }
}
