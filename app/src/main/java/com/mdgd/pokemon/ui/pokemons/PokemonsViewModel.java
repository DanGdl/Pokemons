package com.mdgd.pokemon.ui.pokemons;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.PokemonsRepo;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;
import com.mdgd.pokemon.ui.arch.MviViewModel;
import com.mdgd.pokemon.ui.pokemons.dto.FilterData;
import com.mdgd.pokemon.ui.pokemons.dto.PokemonsScreenState;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PokemonsViewModel extends MviViewModel<PokemonsScreenState> implements PokemonsContract.ViewModel {

    private final PublishSubject<Integer> loadPageSubject = PublishSubject.create();
    private final PublishSubject<FilterData> filtersSubject = PublishSubject.create();
    private final PokemonsContract.Router router;
    private final PokemonsRepo repo;
    private final Cache cache;

    public PokemonsViewModel(PokemonsContract.Router router, PokemonsRepo repo, Cache cache) {
        this.router = router;
        this.repo = repo;
        this.cache = cache;
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

                    filtersSubject
                            .map(filters -> sort(filters, cache.getPokemons()))
                            .subscribe(this::setState, Throwable::printStackTrace)

            );
            loadPageSubject.onNext(0);
        }
    }

    private PokemonsScreenState sort(FilterData filters, List<PokemonDetails> pokemons) {
        Collections.shuffle(pokemons);
//        Collections.sort(pokemons, new Comparator<PokemonDetails>() {
//            @Override
//            public int compare(PokemonDetails pokemon, PokemonDetails t1) {
//                return 0; // todo connect filters
//            }
//        });
        return PokemonsScreenState.createUpdateDataState(pokemons);
    }

    private PokemonsScreenState mapToState(Integer page, Result<List<PokemonDetails>> result) {
        if (result.isError()) {
            return PokemonsScreenState.createErrorState(result.getError());
        } else {
            final List<PokemonDetails> list = result.getValue();
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
    public void onItemClicked(PokemonDetails pokemon) {
        cache.putSelected(pokemon);
        router.proceedToNextScreen();
    }
}
