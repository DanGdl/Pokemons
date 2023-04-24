package com.mdgd.pokemon.ui.pokemon;

import androidx.lifecycle.Lifecycle;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.R;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema;
import com.mdgd.pokemon.models.repo.schemas.Ability;
import com.mdgd.pokemon.models.repo.schemas.Form;
import com.mdgd.pokemon.models.repo.schemas.GameIndex;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.models.repo.schemas.Type;
import com.mdgd.pokemon.ui.pokemon.items.ImagePropertyData;
import com.mdgd.pokemon.ui.pokemon.items.LabelPropertyData;
import com.mdgd.pokemon.ui.pokemon.items.PokemonProperty;
import com.mdgd.pokemon.ui.pokemon.items.TextPropertyData;
import com.mdgd.pokemon.ui.pokemon.items.TitlePropertyData;
import com.mdgd.pokemon.ui.pokemon.state.PokemonScreenState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class PokemonDetailsViewModel extends MviViewModel<PokemonDetailsContract.View, PokemonScreenState> implements PokemonDetailsContract.ViewModel {

    private final BehaviorSubject<Long> pokemonIdSubject = BehaviorSubject.create();
    private final PokemonsRepo repo;

    public PokemonDetailsViewModel(PokemonsRepo repo) {
        this.repo = repo;
    }

    @Override
    public void setPokemonId(long pokemonId) {
        pokemonIdSubject.onNext(pokemonId);
    }

    @Override
    public void onStateChanged(Lifecycle.Event event) {
        super.onStateChanged(event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(pokemonIdSubject
                    .switchMap(repo::getPokemonsById)
                    .map(optional -> optional.isPresent() ? mapToListPokemon(optional.get()) : new LinkedList<PokemonProperty>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> setState(new PokemonScreenState.SetItems(list))));
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
