package com.mdgd.pokemon.ui.pokemon

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.schemas.Ability
import com.mdgd.pokemon.models.repo.schemas.Form
import com.mdgd.pokemon.models.repo.schemas.GameIndex
import com.mdgd.pokemon.models.repo.schemas.Type
import com.mdgd.pokemon.ui.pokemon.infra.*
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class PokemonDetailsViewModel(private val repo: PokemonsRepo) :
    MviViewModel<PokemonDetailsContract.View, PokemonDetailsScreenState>(),
    PokemonDetailsContract.ViewModel {

    private val pokemonIdFlow = MutableStateFlow(-1L)
    private var pokemonLoadingJob: Job? = null

    override fun setPokemonId(pokemonId: Long) {
        viewModelScope.launch {
            pokemonIdFlow.emit(pokemonId)
        }
    }

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE && pokemonLoadingJob == null) {
            pokemonLoadingJob = viewModelScope.launch {
                pokemonIdFlow
                    .filter { it != -1L }
                    .map { id: Long -> repo.getPokemonById(id) }
                    .map { details: PokemonFullDataSchema? ->
                        if (details == null) LinkedList() else mapToListPokemon(
                            details
                        )
                    }
                    .flowOn(Dispatchers.IO)
                    .collect { setState(PokemonDetailsScreenState.SetData(it)) }
            }
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

    override fun onCleared() {
        super.onCleared()
        pokemonLoadingJob = null
    }
}
