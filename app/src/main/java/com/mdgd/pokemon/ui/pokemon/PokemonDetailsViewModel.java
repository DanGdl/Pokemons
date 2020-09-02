package com.mdgd.pokemon.ui.pokemon;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.R;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;
import com.mdgd.pokemon.ui.pokemon.infra.ImagePropertyData;
import com.mdgd.pokemon.ui.pokemon.infra.LabelPropertyData;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;
import com.mdgd.pokemon.ui.pokemon.infra.TextPropertyData;
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

        properties.add(new TitlePropertyData(R.string.pokemon_detail_stats));
        for (Stat s : pokemonDetails.getStats()) {
            properties.add(new LabelPropertyData(s.getStat().getName(), String.valueOf(s.getBaseStat()), 1));
        }
        properties.add(new TitlePropertyData(R.string.pokemon_detail_abilities));
        final List<Ability> abilities = pokemonDetails.getAbilities();
        final StringBuilder abilitiesText = new StringBuilder();
        for (int i = 0; i < abilities.size(); i++) {
            abilitiesText.append(abilities.get(i).getAbility().getName());
            if (i < abilities.size() - 1) {
                abilitiesText.append(", ");
            }
        }
        properties.add(new TextPropertyData(abilitiesText.toString(), 1));

        properties.add(new TitlePropertyData(R.string.pokemon_detail_forms));
        final List<Form> forms = pokemonDetails.getForms();
        final StringBuilder formsText = new StringBuilder();
        for (int i = 0; i < forms.size(); i++) {
            formsText.append(forms.get(i).getName());
            if (i < forms.size() - 1) {
                formsText.append(", ");
            }
        }
        properties.add(new TextPropertyData(formsText.toString(), 1));


        properties.add(new TitlePropertyData(R.string.pokemon_detail_types));
        final List<Type> types = pokemonDetails.getTypes();
        final StringBuilder typesText = new StringBuilder();
        for (int i = 0; i < types.size(); i++) {
            typesText.append(types.get(i).getType().getName());
            if (i < types.size() - 1) {
                typesText.append(", ");
            }
        }
        properties.add(new TextPropertyData(typesText.toString(), 1));


        properties.add(new TitlePropertyData(R.string.pokemon_detail_game_indicies));
        final List<GameIndex> gameIndices = pokemonDetails.getGameIndices();
        final StringBuilder gameIndicesText = new StringBuilder();
        for (int i = 0; i < gameIndices.size(); i++) {
            gameIndicesText.append(gameIndices.get(i).getVersion().getName());
            if (i < gameIndices.size() - 1) {
                gameIndicesText.append(", ");
            }
        }
        properties.add(new TextPropertyData(gameIndicesText.toString(), 1));
        return properties;
    }
}
