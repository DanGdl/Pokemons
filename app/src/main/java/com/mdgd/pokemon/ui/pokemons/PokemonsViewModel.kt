package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.infra.CharacteristicComparator
import com.mdgd.pokemon.ui.pokemons.infra.FilterData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class PokemonsViewModel(private val router: PokemonsContract.Router, private val repo: PokemonsRepo) : MviViewModel<PokemonsScreenState>(), PokemonsContract.ViewModel {
    private val loadPageSubject = PublishSubject.create<Int>()
    private val filtersSubject = BehaviorSubject.createDefault(FilterData())
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
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    loadPageSubject
                            .doOnNext { postState(PokemonsScreenState.Loading()) }
                            .switchMapSingle { page: Int ->
                                repo.getPage(page)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map { result: Result<List<PokemonFullDataSchema>> -> mapToState(page, result) }
                                        .onErrorReturn { error: Throwable? -> PokemonsScreenState.Error(error) }
                            }
                            .subscribe(
                                    { state: PokemonsScreenState -> setState(state) },
                                    { obj: Throwable -> obj.printStackTrace() }),  // do we need to sort list once more when there is a filter and new page arrived?
                    filtersSubject
                            .map { filters: FilterData -> sort(filters, repo.getPokemons()) }
                            .subscribe({ state: PokemonsScreenState -> setState(state) }, { obj: Throwable -> obj.printStackTrace() })
            )
            loadPageSubject.onNext(0)
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

    private fun mapToState(page: Int, result: Result<List<PokemonFullDataSchema>>): PokemonsScreenState {
        return if (result.isError()) {
            PokemonsScreenState.Error(result.getError())
        } else {
            val list = result.getValue()
            if (page == 0) {
                PokemonsScreenState.SetData(list)
            } else {
                PokemonsScreenState.AddData(list)
            }
        }
    }

    override fun reload() {
        loadPageSubject.onNext(0)
    }

    override fun loadPage(page: Int) {
        loadPageSubject.onNext(page)
    }

    override fun sort(filterData: FilterData) {
        filtersSubject.onNext(filterData)
    }

    override fun onItemClicked(pokemon: PokemonFullDataSchema) {
        router.proceedToNextScreen(pokemon.pokemonSchema?.id)
    }
}
