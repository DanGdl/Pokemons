package com.mdgd.pokemon.ui.pokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mdgd.mvi.HostedFragment;
import com.mdgd.pokemon.PokemonsApp;
import com.mdgd.pokemon.R;
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertiesAdapter;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState;
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty;

import java.util.List;

public class PokemonDetailsFragment extends HostedFragment<PokemonDetailsScreenState, PokemonDetailsContract.ViewModel, PokemonDetailsContract.Host>
        implements PokemonDetailsContract.View {

    private final PokemonPropertiesAdapter adapter = new PokemonPropertiesAdapter();

    public static PokemonDetailsFragment newInstance() {
        return new PokemonDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getModel().setPokemonId(PokemonDetailsFragmentArgs.fromBundle(getArguments()).getPokemonId());
        }
    }

    @Override
    protected PokemonDetailsContract.ViewModel createModel() {
        return new ViewModelProvider(this, new PokemonDetailsViewModelFactory(PokemonsApp.getInstance().getAppComponent())).get(PokemonDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pokemon_properties, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recycler = view.findViewById(R.id.pokemon_details_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void setItems(List<PokemonProperty> items) {
        adapter.setItems(items);
    }
}
