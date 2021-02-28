package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.mvi.util.DispatchersHolder
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class PokemonsViewModel(private val repo: PokemonsRepo, private val filtersFactory: StatsFilter, private val dispatchers: DispatchersHolder)
    : MviViewModel<PokemonsScreenState>(), PokemonsContract.ViewModel {

    private val pageFlow = MutableStateFlow(0)
    private val filterFlow = MutableStateFlow(FilterData())
    private var launch: Job? = null

    override fun getDefaultState(): PokemonsScreenState {
        return PokemonsScreenState.Initial(filtersFactory.getAvailableFilters())
    }

    public override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_CREATE && launch == null) {
            launch = viewModelScope.launch {
                pageFlow
                        .onEach { setState(PokemonsScreenState.Loading(getLastState())) }
                        .flowOn(dispatchers.getMain())
                        .map { page -> Pair(page, repo.getPage(page)) }
                        .flowOn(dispatchers.getIO())
                        .catch { e: Throwable -> setState(PokemonsScreenState.Error(e, getLastState())) }
                        .collect { pagePair: Pair<Int, List<PokemonFullDataSchema>> ->
                            if (pagePair.first == 0) {
                                setState(PokemonsScreenState.SetData(pagePair.second, getLastState()))
                            } else {
                                setState(PokemonsScreenState.AddData(pagePair.second, getLastState()))
                            }
                        }
            }

            viewModelScope.launch {
                filterFlow
                        .map { sort(it, repo.getPokemons()) }
                        .collect { sortedList -> setState(PokemonsScreenState.UpdateData(sortedList, getLastState())) }
            }
        }
    }

    private fun sort(filters: FilterData, pokemons: List<PokemonFullDataSchema>): List<PokemonFullDataSchema> {
        // potentially, we can create a custom list of filters in separate model. In UI we can show them in recyclerView
        if (!filters.isEmpty) {
            val comparators = filtersFactory.getFilters()
            Collections.sort(pokemons) { pokemon1: PokemonFullDataSchema?, pokemon2: PokemonFullDataSchema? ->
                var compare = 0
                for (filter in filters.filters) {
                    compare = comparators[filter]?.compare(pokemon2!!, pokemon1!!)
                            ?: 0 // swap, instead of multiply on -1
                    if (compare != 0) {
                        break
                    }
                }
                compare
            }
        }
        return pokemons
    }

    override fun reload() {
        viewModelScope.launch {
            pageFlow.emit(0)
        }
    }

    override fun loadPage(page: Int) {
        setState(PokemonsScreenState.Loading(getLastState()))
        viewModelScope.launch {
            pageFlow.emit(page)
        }
    }

    override fun sort(filter: String) {
        setState(PokemonsScreenState.ChangeFilterState(filter, getLastState()))
        viewModelScope.launch {
            filterFlow.emit(FilterData(getLastState().getActiveFilters()))
        }
    }

    override fun onItemClicked(pokemon: PokemonFullDataSchema) {
        setState(PokemonsScreenState.ShowDetails(pokemon.pokemonSchema?.id, getLastState()))
    }

    public override fun onCleared() {
        super.onCleared()
        launch = null
    }
}
