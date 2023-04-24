package com.mdgd.pokemon.ui.pokemons;

import androidx.core.util.Pair;
import androidx.lifecycle.Lifecycle;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.models.filters.CharacteristicComparator;
import com.mdgd.pokemon.models.filters.FilterData;
import com.mdgd.pokemon.models.filters.StatsFilter;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenEffect;
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsViewModel extends MviViewModel<PokemonsContract.View, PokemonsScreenState> implements PokemonsContract.ViewModel {

    private final PublishSubject<Integer> loadPageSubject = PublishSubject.create();
    private final BehaviorSubject<FilterData> filtersSubject = BehaviorSubject.createDefault(new FilterData());
    private final StatsFilter statsFilters;
    private final PokemonsRepo repo;

    public PokemonsViewModel(PokemonsRepo repo, StatsFilter statsFilters) {
        this.repo = repo;
        this.statsFilters = statsFilters;
    }

    @Override
    public void onStateChanged(Lifecycle.Event event) {
        super.onStateChanged(event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    loadPageSubject
                            .doOnNext(ignore -> setState(new PokemonsScreenState.SetProgressVisibility(true)))
                            .switchMapSingle(page -> repo.getPage(page)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(result -> new Pair<>(page, result))
                            )
                            .subscribe(this::handleResult, e -> setEffect(new PokemonsScreenEffect.ShowErrorEffect(e))),

                    // do we need to sort list once more when there is a filter and new page arrived?
                    filtersSubject
                            .map(filters -> sort(filters, repo.getPokemons()))
                            .subscribe(this::setState, e -> setEffect(new PokemonsScreenEffect.ShowErrorEffect(e)))

            );
            loadPageSubject.onNext(0);
        }
    }

    private PokemonsScreenState sort(FilterData filters, List<PokemonFullDataSchema> pokemons) {
        // potentially, we can create a custom list of filters in separate model. In UI we can show them in recyclerView
        if (!filters.isEmpty()) {
            final Map<String, CharacteristicComparator> comparatorMap = statsFilters.getFilters();
            Collections.sort(pokemons, (pokemon1, pokemon2) -> {
                int compare = 0;
                for (String filter : filters.getFilters()) {
                    if (comparatorMap.get(filter) != null) {
                        compare = comparatorMap.get(filter).compare(pokemon2, pokemon1); // swap, instead of multiply on -1
                        if (compare != 0) {
                            break;
                        }
                    }
                }
                return compare;
            });
        }
        return new PokemonsScreenState.SetItems(pokemons, statsFilters.getAvailableFilters());
    }

    private void handleResult(Pair<Integer, Result<List<PokemonFullDataSchema>>> pair) {
        if (pair.second.isError()) {
            setEffect(new PokemonsScreenEffect.ShowErrorEffect(pair.second.getError()));
        } else {
            final List<PokemonFullDataSchema> list = pair.second.getValue();
            if (pair.first == 0) {
                setState(new PokemonsScreenState.SetItems(list, statsFilters.getAvailableFilters()));
            } else {
                setState(new PokemonsScreenState.AddItems(list));
            }
        }
    }


    @Override
    public void reload() {
        loadPageSubject.onNext(0);
    }

    @Override
    public void loadPage(int page) {
        loadPageSubject.onNext(page);
    }

    @Override
    public void sort(FilterData filterData) {
        filtersSubject.onNext(filterData);
    }

    @Override
    public void onItemClicked(PokemonFullDataSchema pokemon) {
        setEffect(new PokemonsScreenEffect.ShowDetails(pokemon.getPokemonSchema().getId()));
    }
}
