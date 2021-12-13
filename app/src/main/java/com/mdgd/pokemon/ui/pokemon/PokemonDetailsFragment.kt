package com.mdgd.pokemon.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.PokemonsApp.Companion.instance
import com.mdgd.pokemon.R
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertiesAdapter
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenEffect
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState

class PokemonDetailsFragment : HostedFragment<
        PokemonDetailsContract.View,
        PokemonDetailsScreenState,
        PokemonDetailsScreenEffect,
        PokemonDetailsContract.ViewModel,
        PokemonDetailsContract.Host>(), PokemonDetailsContract.View {

    private val adapter = PokemonPropertiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model?.setPokemonId(PokemonDetailsFragmentArgs.fromBundle(requireArguments()).pokemonId)
        }
    }

    override fun createModel(): PokemonDetailsContract.ViewModel {
        return ViewModelProvider(
            this, PokemonDetailsViewModelFactory(instance!!.appComponent!!)
        )[PokemonDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_properties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler: RecyclerView = view.findViewById(R.id.pokemon_details_recycler)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }

    override fun setItems(items: List<PokemonProperty>) {
        adapter.setItems(items)
    }
}
