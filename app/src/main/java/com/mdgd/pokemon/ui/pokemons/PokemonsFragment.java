package com.mdgd.pokemon.ui.pokemons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.arch.HostedFragment;

public class PokemonsFragment extends HostedFragment<PokemonsContract.ViewModel, PokemonsContract.Host> implements PokemonsContract.View, PokemonsContract.Router {

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
    }

    @Override
    public void proceedToNextScreen() {

    }
}
