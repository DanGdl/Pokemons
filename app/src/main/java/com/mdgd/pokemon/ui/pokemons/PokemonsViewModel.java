package com.mdgd.pokemon.ui.pokemons;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.schemas.Stat;
import com.mdgd.pokemon.ui.arch.MviViewModel;
import com.mdgd.pokemon.ui.pokemons.infra.CharacteristicComparator;
import com.mdgd.pokemon.ui.pokemons.infra.FilterData;
import com.mdgd.pokemon.ui.pokemons.infra.PokemonsScreenState;

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
    private final PokemonsContract.Router router;
    private final PokemonsRepo repo;
    private final Cache cache;
    private final Map<String, CharacteristicComparator> comparators = new HashMap<String, CharacteristicComparator>() {{
        put(FilterData.FILTER_ATTACK, (p1, p2) -> compareProperty("attack", p1, p2));
        put(FilterData.FILTER_DEFENCE, (p1, p2) -> compareProperty("defense", p1, p2));
        put(FilterData.FILTER_SPEED, (p1, p2) -> compareProperty("speed", p1, p2));
    }};

    public PokemonsViewModel(PokemonsContract.Router router, PokemonsRepo repo, Cache cache) {
        this.router = router;
        this.repo = repo;
        this.cache = cache;
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
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    loadPageSubject
                            .doOnNext(ignore -> postState(PokemonsScreenState.createLoadingState()))
                            .switchMapSingle(page -> repo.getPage(page)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(result -> mapToState(page, result))
                                    .onErrorReturn(PokemonsScreenState::createErrorState))
                            .subscribe(this::setState, Throwable::printStackTrace),

                    // do we need to sort list once more when there is a filter and new page arrived?
                    filtersSubject
                            .map(filters -> sort(filters, cache.getPokemons()))
                            .subscribe(this::setState, Throwable::printStackTrace)

            );
            loadPageSubject.onNext(0);
        }
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
            return PokemonsScreenState.createErrorState(result.getError());
        } else {
            final List<PokemonFullDataSchema> list = result.getValue();
            if (page == 0) {
                cache.setPokemons(list);
                return PokemonsScreenState.createSetDataState(list);
            } else {
                cache.addPokemons(list);
                return PokemonsScreenState.createAddDataState(list);
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
        cache.putSelected(pokemon);
        router.proceedToNextScreen();
    }
}
