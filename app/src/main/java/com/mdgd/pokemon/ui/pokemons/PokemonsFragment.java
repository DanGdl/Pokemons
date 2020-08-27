package com.mdgd.pokemon.ui.pokemons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.arch.HostedFragment;
import com.mdgd.pokemon.ui.pokemons.dto.FilterData;
import com.mdgd.pokemon.ui.pokemons.dto.PokemonsScreen;
import com.mdgd.pokemon.ui.pokemons.dto.PokemonsScreenState;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PokemonsFragment extends HostedFragment<PokemonsContract.ViewModel, PokemonsContract.Host>
        implements PokemonsContract.View, PokemonsContract.Router, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        CompoundButton.OnCheckedChangeListener, Observer<PokemonsScreenState>, PokemonsScreen {

    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final PokemonsAdapter adapter = new PokemonsAdapter();
    private SwipeRefreshLayout refreshSwipe;
    private ToggleButton filterAttack;
    private ToggleButton filterDefence;
    private ToggleButton filterMovement;
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

        final RecyclerView recyclerView = view.findViewById(R.id.pokemons_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // todo request next page
            }
        });
        recyclerView.setAdapter(adapter);
        refresh = view.findViewById(R.id.pokemons_refresh);
        refreshSwipe = view.findViewById(R.id.pokemons_swipe_refresh);
        filterAttack = view.findViewById(R.id.pokemons_filter_attack);
        filterDefence = view.findViewById(R.id.pokemons_filter_defence);
        filterMovement = view.findViewById(R.id.pokemons_filter_movement);

        refresh.setOnClickListener(this);
        refreshSwipe.setOnRefreshListener(this);
        filterAttack.setOnCheckedChangeListener(this);
        filterDefence.setOnCheckedChangeListener(this);
        filterMovement.setOnCheckedChangeListener(this);
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
        getModel().sort(new FilterData(filterAttack.isChecked(), filterDefence.isChecked(), filterMovement.isChecked()));
    }

    @Override
    public void onChanged(PokemonsScreenState pokemonsScreenState) {
        pokemonsScreenState.visit(this);
    }

    @Override
    public void onDestroy() {
        onDestroyDisposables.clear();
        super.onDestroy();
    }
}
