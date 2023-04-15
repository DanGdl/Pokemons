package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.mvi.util.DispatchersHolder
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenEffect
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonsViewModel(
    private val repo: PokemonsRepo, private val filtersFactory: StatsFilter,
    private val dispatchers: DispatchersHolder
) : MviViewModel<PokemonsContract.View, PokemonsScreenState>(),
    PokemonsContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        setEffect(PokemonsScreenEffect.Error(e))
    }
    private val pageFlow = MutableStateFlow(0)
    private val filterFlow = MutableStateFlow(FilterData())
    private var launch: Job? = null

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE && launch == null) {
            launch = viewModelScope.launch(exceptionHandler) {
                pageFlow
                    .onEach { setState(PokemonsScreenState.Loading()) }
                    .flowOn(dispatchers.getMain())
                    .map { page -> Pair(page, repo.getPage(page)) }
                    .flowOn(dispatchers.getIO())
                    .catch { e: Throwable -> setEffect(PokemonsScreenEffect.Error(e)) }
                    .collect { pagePair: Pair<Int, List<PokemonFullDataSchema>> ->
                        if (pagePair.first == 0) {
                            setState(
                                PokemonsScreenState.SetData(
                                    pagePair.second, filtersFactory.getAvailableFilters()
                                )
                            )
                        } else {
                            setState(PokemonsScreenState.AddData(pagePair.second))
                        }
                    }
            }

            viewModelScope.launch(exceptionHandler) {
                filterFlow
                    .map { sort(it, repo.getPokemons()) }
                    .collect { sortedList ->
                        setState(PokemonsScreenState.UpdateData(sortedList))
                        setEffect(PokemonsScreenEffect.ScrollToStart())
                    }
            }
        }
    }

    private fun sort(
        filters: FilterData, pokemons: List<PokemonFullDataSchema>
    ): List<PokemonFullDataSchema> {
        val list = ArrayList(pokemons)
        if (!filters.isEmpty) {
            val comparators = filtersFactory.getFilters()
            list.sortWith { pokemon1: PokemonFullDataSchema?, pokemon2: PokemonFullDataSchema? ->
                var compare = 0
                for (filter in filters.filters) {
                    // swap, instead of multiply on -1
                    compare = comparators[filter]?.compare(pokemon2!!, pokemon1!!) ?: 0
                    if (compare != 0) {
                        break
                    }
                }
                compare
            }
        }
        return list
    }

    override fun reload() {
        pageFlow.tryEmit(0)
    }

    override fun loadPage(page: Int) {
        pageFlow.tryEmit(page)
    }

    override fun sort(filter: String) {
        val filters = getState()?.activeFilters?.toMutableList() ?: mutableListOf()
        if (filters.contains(filter)) {
            filters.remove(filter)
        } else {
            filters.add(filter)
        }
        setState(PokemonsScreenState.ChangeFilterState(filters))
        filterFlow.tryEmit(FilterData(filters))
    }

    override fun onItemClicked(pokemon: PokemonFullDataSchema) {
        setEffect(PokemonsScreenEffect.ShowDetails(pokemon.pokemonSchema?.id))
    }

    public override fun onCleared() {
        launch = null
        super.onCleared()
    }
}
