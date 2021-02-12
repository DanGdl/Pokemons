package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.infra.CharacteristicComparator
import com.mdgd.pokemon.ui.pokemons.infra.FilterData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.util.*

class PokemonsViewModel(private val router: PokemonsContract.Router, private val repo: PokemonsRepo) : MviViewModel<PokemonsScreenState>(), PokemonsContract.ViewModel {
    private val pageFlow = MutableStateFlow(0)
    private val filterFlow = MutableStateFlow(FilterData())
    private val comparators: Map<String, CharacteristicComparator?> = object : HashMap<String, CharacteristicComparator>() {
        init {
            put(FilterData.FILTER_ATTACK, object : CharacteristicComparator {
                override fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
                    return compareProperty("attack", p1, p2)
                }
            })
            put(FilterData.FILTER_DEFENCE, object : CharacteristicComparator {
                override fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
                    return compareProperty("defense", p1, p2)
                }
            })
            put(FilterData.FILTER_SPEED, object : CharacteristicComparator {
                override fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
                    return compareProperty("speed", p1, p2)
                }
            })
        }
    }
    private var launch: Job? = null


    private fun compareProperty(property: String, p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int {
        var val1 = -1
        for (s in p1.stats) {
            if (property == s.stat!!.name) {
                val1 = s.baseStat!!
            }
        }
        var val2 = -1
        for (s in p2.stats) {
            if (property == s.stat!!.name) {
                val2 = s.baseStat!!
            }
        }
        return val1.compareTo(val2)
    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_CREATE && launch == null) {

            // TODO: use flow operators
            launch = viewModelScope.launch {
                pageFlow.collect { page: Int ->
                    setState(PokemonsScreenState.Loading())
                    supervisorScope {
                        try {
                            val pageData = withContext(Dispatchers.IO) {
                                repo.getPage(page)
                            }
                            if (page == 0) {
                                setState(PokemonsScreenState.SetData(pageData))
                            } else {
                                setState(PokemonsScreenState.AddData(pageData))
                            }
                        } catch (e: Throwable) {
                            e.printStackTrace()
                            setState(PokemonsScreenState.Error(e))
                        }
                    }
                }
            }

            viewModelScope.launch {
                filterFlow.collect { data ->
                    setState(sort(data, repo.getPokemons()))
                }
            }
        }
    }

    private fun sort(filters: FilterData, pokemons: List<PokemonFullDataSchema>): PokemonsScreenState {
        // potentially, we can create a custom list of filters in separate model. In UI we can show them in recyclerView
        if (!filters.isEmpty) {
            Collections.sort(pokemons) { pokemon1: PokemonFullDataSchema?, pokemon2: PokemonFullDataSchema? ->
                var compare = 0
                for (filter in filters.filters) {
                    if (comparators[filter] != null) {
                        compare = comparators[filter]!!.compare(pokemon2!!, pokemon1!!) // swap, instead of multiply on -1
                        if (compare != 0) {
                            break
                        }
                    }
                }
                compare
            }
        }
        return PokemonsScreenState.UpdateData(pokemons)
    }

    override fun reload() {
        viewModelScope.launch {
            pageFlow.emit(0)
        }
    }

    override fun loadPage(page: Int) {
        viewModelScope.launch {
            pageFlow.emit(page)
        }
    }

    override fun sort(filterData: FilterData) {
        viewModelScope.launch {
            filterFlow.emit(filterData)
        }
    }

    override fun onItemClicked(pokemon: PokemonFullDataSchema) {
        router.proceedToNextScreen(pokemon.pokemonSchema?.id)
    }
}
