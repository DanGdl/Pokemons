package com.mdgd.pokemon.ui.pokemon

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.common.base.Optional
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.schemas.Ability
import com.mdgd.pokemon.models.repo.schemas.Form
import com.mdgd.pokemon.models.repo.schemas.GameIndex
import com.mdgd.pokemon.models.repo.schemas.Type
import com.mdgd.pokemon.ui.pokemon.infra.*
import java.util.*

class PokemonDetailsViewModel(private val cache: Cache) : MviViewModel<PokemonDetailsScreenState>(), PokemonDetailsContract.ViewModel {

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(cache.getSelectedPokemonObservable()
                    .map { optional: Optional<PokemonFullDataSchema> -> if (optional.isPresent) mapToListPokemon(optional.get()) else LinkedList() }
                    .subscribe { list: List<PokemonProperty> -> setState(PokemonDetailsScreenState.createSetDataState(list)) })
        }
    }

    private fun mapToListPokemon(pokemonDetails: PokemonFullDataSchema): List<PokemonProperty> {
        val properties: MutableList<PokemonProperty> = ArrayList()
        val pokemonSchema = pokemonDetails.pokemonSchema
        properties.add(ImagePropertyData(pokemonSchema!!.sprites!!.other!!.officialArtwork!!.frontDefault!!))
        properties.add(LabelPropertyData(R.string.pokemon_detail_name, pokemonSchema.name!!))
        properties.add(LabelPropertyData(R.string.pokemon_detail_height, pokemonSchema.height.toString()))
        properties.add(LabelPropertyData(R.string.pokemon_detail_weight, pokemonSchema.weight.toString()))
        properties.add(TitlePropertyData(R.string.pokemon_detail_stats))
        for (s in pokemonDetails.stats) {
            properties.add(LabelPropertyData(s.stat!!.name!!, s.baseStat.toString(), 1))
        }
        properties.add(TitlePropertyData(R.string.pokemon_detail_abilities))
        val abilities: List<Ability> = pokemonDetails.abilities
        val abilitiesText = StringBuilder()
        for (i in abilities.indices) {
            abilitiesText.append(abilities[i].ability!!.name)
            if (i < abilities.size - 1) {
                abilitiesText.append(", ")
            }
        }
        properties.add(TextPropertyData(abilitiesText.toString(), 1))
        properties.add(TitlePropertyData(R.string.pokemon_detail_forms))
        val forms: List<Form> = pokemonDetails.forms
        val formsText = StringBuilder()
        for (i in forms.indices) {
            formsText.append(forms[i].name)
            if (i < forms.size - 1) {
                formsText.append(", ")
            }
        }
        properties.add(TextPropertyData(formsText.toString(), 1))
        properties.add(TitlePropertyData(R.string.pokemon_detail_types))
        val types: List<Type> = pokemonDetails.types
        val typesText = StringBuilder()
        for (i in types.indices) {
            typesText.append(types[i].type!!.name)
            if (i < types.size - 1) {
                typesText.append(", ")
            }
        }
        properties.add(TextPropertyData(typesText.toString(), 1))
        properties.add(TitlePropertyData(R.string.pokemon_detail_game_indicies))
        val gameIndices: List<GameIndex> = pokemonDetails.gameIndices
        val gameIndicesText = StringBuilder()
        for (i in gameIndices.indices) {
            gameIndicesText.append(gameIndices[i].version!!.name)
            if (i < gameIndices.size - 1) {
                gameIndicesText.append(", ")
            }
        }
        properties.add(TextPropertyData(gameIndicesText.toString(), 1))
        return properties
    }
}
