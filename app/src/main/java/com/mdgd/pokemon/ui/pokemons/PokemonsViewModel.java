package com.mdgd.pokemon.ui.pokemons;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.ui.pokemons.infra.CharacteristicComparator;
import com.mdgd.pokemon.ui.pokemons.infra.FilterData;
import com.mdgd.pokemon.ui.pokemons.infra.PokemonsScreenState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsViewModel extends MviViewModel<PokemonsScreenState> implements PokemonsContract.ViewModel {

    private final PublishSubject<Integer> loadPageSubject = PublishSubject.create();
    private final BehaviorSubject<FilterData> filtersSubject = BehaviorSubject.createDefault(new FilterData());
    private final PokemonsRepo repo;
    private final Map<String, CharacteristicComparator> comparators = new HashMap<String, CharacteristicComparator>() {{
        put(FilterData.FILTER_ATTACK, (p1, p2) -> compareProperty("attack", p1, p2));
        put(FilterData.FILTER_DEFENCE, (p1, p2) -> compareProperty("defense", p1, p2));
        put(FilterData.FILTER_SPEED, (p1, p2) -> compareProperty("speed", p1, p2));
    }};

    public PokemonsViewModel(PokemonsRepo repo) {
        this.repo = repo;
    }

    private int compareProperty(String property, PokemonFullDataSchema p1, PokemonFullDataSchema p2) {
        int val1 = -1;
        for (Stat s : p1.getStats()) {
            if (property.equals(s.getStat().getName())) {
                val1 = s.getBaseStat();
            }
        }
        int val2 = -1;
        for (Stat s : p2.getStats()) {
            if (property.equals(s.getStat().getName())) {
                val2 = s.getBaseStat();
            }
        }
        return Integer.compare(val1, val2);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    loadPageSubject
                            .doOnNext(ignore -> postState(PokemonsScreenState.createLoadingState(getLastState())))
                            .switchMapSingle(page -> repo.getPage(page)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(result -> mapToState(page, result))
                                    .onErrorReturn(e -> PokemonsScreenState.createErrorState(e, getLastState())))
                            .subscribe(this::setState, Throwable::printStackTrace),

                    // do we need to sort list once more when there is a filter and new page arrived?
                    filtersSubject
                            .map(filters -> sort(filters, repo.getPokemons()))
                            .subscribe(this::setState, Throwable::printStackTrace)

            );
            loadPageSubject.onNext(0);
        }
    }

    @Override
    protected PokemonsScreenState getDefaultState() {
        return PokemonsScreenState.createSetDataState(new ArrayList<>(0));
    }

    private PokemonsScreenState sort(FilterData filters, List<PokemonFullDataSchema> pokemons) {
        // potentially, we can create a custom list of filters in separate model. In UI we can show them in recyclerView
        if (!filters.isEmpty()) {
            Collections.sort(pokemons, (pokemon1, pokemon2) -> {
                int compare = 0;
                for (String filter : filters.getFilters()) {
                    if (comparators.get(filter) != null) {
                        compare = comparators.get(filter).compare(pokemon2, pokemon1); // swap, instead of multiply on -1
                        if (compare != 0) {
                            break;
                        }
                    }
                }
                return compare;
            });
        }
        return PokemonsScreenState.createUpdateDataState(pokemons);
    }

    private PokemonsScreenState mapToState(Integer page, Result<List<PokemonFullDataSchema>> result) {
        if (result.isError()) {
            return PokemonsScreenState.createErrorState(result.getError(), getLastState());
        } else {
            final List<PokemonFullDataSchema> list = result.getValue();
            if (page == 0) {
                return PokemonsScreenState.createSetDataState(list);
            } else {
                return PokemonsScreenState.createAddDataState(list, getLastState());
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
        setState(PokemonsScreenState.createShowDetails(pokemon.getPokemonSchema().getId(), getLastState()));
    }
}
