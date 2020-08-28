package com.mdgd.pokemon.ui.pokemons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;
import com.mdgd.pokemon.ui.arch.HostedFragment;
import com.mdgd.pokemon.ui.pokemons.infra.FilterData;
import com.mdgd.pokemon.ui.pokemons.infra.PokemonsScreen;
import com.mdgd.pokemon.ui.pokemons.infra.PokemonsScreenState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PokemonsFragment extends HostedFragment<PokemonsScreenState, PokemonsContract.ViewModel, PokemonsContract.Host>
        implements PokemonsContract.View, PokemonsContract.Router, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        CompoundButton.OnCheckedChangeListener, PokemonsScreen {

    private final List<String> filters = new ArrayList<>(3);
    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final PokemonsAdapter adapter = new PokemonsAdapter();
    private SwipeRefreshLayout refreshSwipe;
    private final EndlessScrollListener scrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            if (refreshSwipe != null) {
                refreshSwipe.setRefreshing(true);
            }
            getModel().loadPage(page);
        }
    };
    private RecyclerView recyclerView;
    private ToggleButton filterAttack;
    private ToggleButton filterDefence;
    private ToggleButton filterSpeed;
    private View refresh;

    public static PokemonsFragment newInstance() {
        return new PokemonsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(new ViewModelProvider(this, new PokemonsViewModelFactory(this)).get(PokemonsViewModel.class));
        getLifecycle().addObserver(getModel());
        getModel().getStateObservable().observe(this, this);

        onDestroyDisposables.add(adapter.getOnItemClickSubject().subscribe(pokemon -> getModel().onItemClicked(pokemon)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pokemons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.pokemons_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(adapter);
        refresh = view.findViewById(R.id.pokemons_refresh);
        refreshSwipe = view.findViewById(R.id.pokemons_swipe_refresh);
        filterAttack = view.findViewById(R.id.pokemons_filter_attack);
        filterDefence = view.findViewById(R.id.pokemons_filter_defence);
        filterSpeed = view.findViewById(R.id.pokemons_filter_movement);

        refresh.setOnClickListener(this);
        refreshSwipe.setOnRefreshListener(this);

        filterAttack.setOnCheckedChangeListener(this);
        filterDefence.setOnCheckedChangeListener(this);
        filterSpeed.setOnCheckedChangeListener(this);
    }

    @Override
    public void proceedToNextScreen() {
        if (hasHost()) {
            getFragmentHost().proceedToPokemonScreen();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == refresh) {
            refreshSwipe.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        getModel().reload();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (filterAttack == compoundButton) {
                filters.add(FilterData.FILTER_ATTACK);
            } else if (filterDefence == compoundButton) {
                filters.add(FilterData.FILTER_DEFENCE);
            } else if (filterSpeed == compoundButton) {
                filters.add(FilterData.FILTER_SPEED);
            }
        } else {
            if (filterAttack == compoundButton) {
                filters.remove(FilterData.FILTER_ATTACK);
            } else if (filterDefence == compoundButton) {
                filters.remove(FilterData.FILTER_DEFENCE);
            } else if (filterSpeed == compoundButton) {
                filters.remove(FilterData.FILTER_SPEED);
            }
        }
        getModel().sort(new FilterData(new ArrayList<>(filters)));
    }

    @Override
    public void onDestroy() {
        onDestroyDisposables.clear();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        if (refreshSwipe != null) {
            refreshSwipe.setRefreshing(true);
        }
    }

    @Override
    public void hideProgress() {
        if (refreshSwipe != null) {
            refreshSwipe.setRefreshing(false);
        }
    }

    @Override
    public void setItems(List<PokemonDetails> list) {
        adapter.setItems(list);
    }

    @Override
    public void addItems(List<PokemonDetails> list) {
        adapter.addItems(list);
    }

    @Override
    public void updateItems(List<PokemonDetails> list) {
        adapter.updateItems(list);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void showError(Throwable error) {
        if (hasHost()) {
            getFragmentHost().showError(error);
        }
    }
}
