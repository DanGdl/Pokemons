package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.filters.StatsFilter
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.util.DispatchersHolder
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenAction
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val repo: PokemonsRepo,
    private val filtersFactory: StatsFilter,
    private val dispatchers: DispatchersHolder
) : MviViewModel<PokemonsContract.View, PokemonsScreenState, PokemonsScreenAction>(),
    PokemonsContract.ViewModel {

    private var firstVisibleIndex: Int = 0
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        setAction(PokemonsScreenAction.Error(e))
    }
    private val pageFlow = MutableStateFlow(0)
    private val filterFlow = MutableStateFlow(FilterData())
    private var launch: Job? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        super.onStateChanged(source, event)
        if (event == Lifecycle.Event.ON_CREATE && launch == null) {
            launch = viewModelScope.launch(exceptionHandler) {
                pageFlow
                    .onEach { setState(PokemonsScreenState.Loading()) }
                    .flowOn(dispatchers.getMain())
                    .map { page -> Pair(page, repo.getPage(page)) }
                    .flowOn(dispatchers.getIO())
                    .catch { e: Throwable -> setAction(PokemonsScreenAction.Error(e)) }
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
                        firstVisibleIndex = 0
                        setState(PokemonsScreenState.UpdateData(sortedList))
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
                    compare = comparators[filter]?.compare(pokemon2!!, pokemon1!!)
                        ?: 0 // swap, instead of multiply on -1
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

    override fun sort(filter: String) {
        val filters = ArrayList(getState()?.getActiveFilters() ?: listOf())
        if (filters.contains(filter)) {
            filters.remove(filter)
        } else {
            filters.add(filter)
        }
        setState(PokemonsScreenState.ChangeFilterState(filters))
        filterFlow.tryEmit(FilterData(getState()?.getActiveFilters() ?: listOf()))
    }

    override fun onItemClicked(pokemon: PokemonFullDataSchema) {
        setAction(PokemonsScreenAction.ShowDetails(pokemon.pokemonSchema?.id))
    }

    override fun onScroll(firstVisibleIndex: Int, lastVisibleIndex: Int) {
        this.firstVisibleIndex = firstVisibleIndex
        val page = pageFlow.value + 1
        if (lastVisibleIndex >= page * PokemonsRepo.PAGE_SIZE - 8) {
            pageFlow.tryEmit(page)
        }
    }

    override fun firstVisible() = firstVisibleIndex

    public override fun onCleared() {
        launch = null
        super.onCleared()
    }
}
