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

public class PokemonsFragment extends HostedFragment<PokemonsContract.ViewModel, PokemonsContract.Host>
        implements PokemonsContract.View, PokemonsContract.Router, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CompoundButton.OnCheckedChangeListener {

    private final PokemonsAdapter adapter = new PokemonsAdapter();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshSwipe;
    private ToggleButton filterAttack;
    private ToggleButton filterDefence;
    private ToggleButton filterMovement;

    public static PokemonsFragment newInstance() {
        return new PokemonsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(new ViewModelProvider(this, new PokemonsViewModelFactory(this)).get(PokemonsViewModel.class));
        getLifecycle().addObserver(getModel());
        getModel().getStateObservable().observe(this, new Observer<PokemonsScreenState>() {
            @Override
            public void onChanged(PokemonsScreenState pokemonsScreenState) {

            }
        });
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
        recyclerView.setAdapter(adapter);
        refreshSwipe = view.findViewById(R.id.pokemons_swipe_refresh);
        filterAttack = view.findViewById(R.id.pokemons_filter_attack);
        filterDefence = view.findViewById(R.id.pokemons_filter_defence);
        filterMovement = view.findViewById(R.id.pokemons_filter_movement);

        view.findViewById(R.id.pokemons_refresh).setOnClickListener(this);
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
        // todo launch refresh
    }

    @Override
    public void onRefresh() {
// todo launch refresh
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // todo launch filtering
    }
}
