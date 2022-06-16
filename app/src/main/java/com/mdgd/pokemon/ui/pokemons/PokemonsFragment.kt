package com.mdgd.pokemon.ui.pokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.PokemonsApp
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.adapter.ClickEvent
import com.mdgd.pokemon.ui.pokemons.adapter.PokemonsAdapter
import com.mdgd.pokemon.ui.pokemons.infra.EndlessScrollListener
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenEffect
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
import kotlinx.coroutines.launch

class PokemonsFragment : HostedFragment<
        PokemonsContract.View,
        PokemonsScreenState,
        PokemonsScreenEffect,
        PokemonsContract.ViewModel,
        PokemonsContract.Host>(),
    PokemonsContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val adapter = PokemonsAdapter(lifecycleScope)
    private var refreshSwipe: SwipeRefreshLayout? = null

    // maybe paging library?
    private val scrollListener: EndlessScrollListener = object : EndlessScrollListener() {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            model?.loadPage(page)
        }
    }
    private var recyclerView: RecyclerView? = null
    private var filterAttack: ImageButton? = null
    private var filterDefence: ImageButton? = null
    private var filterSpeed: ImageButton? = null
    private var refresh: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            adapter.getItemClickFlow().collect {
                if (it is ClickEvent.ClickData) {
                    model?.onItemClicked(it.data)
                }
            }
        }
    }

    override fun createModel(): PokemonsContract.ViewModel {
        return ViewModelProvider(
            this, PokemonsViewModelFactory(PokemonsApp.instance?.appComponent!!)
        )[PokemonsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.pokemons_recycler)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addOnScrollListener(scrollListener)
        recyclerView?.adapter = adapter

        refresh = view.findViewById(R.id.pokemons_refresh)
        refreshSwipe = view.findViewById(R.id.pokemons_swipe_refresh)
        filterAttack = view.findViewById(R.id.pokemons_filter_attack)
        filterDefence = view.findViewById(R.id.pokemons_filter_defence)
        filterSpeed = view.findViewById(R.id.pokemons_filter_movement)

        refresh?.setOnClickListener(this)
        refreshSwipe?.setOnRefreshListener(this)
        filterAttack?.setOnClickListener(this)
        filterDefence?.setOnClickListener(this)
        filterSpeed?.setOnClickListener(this)
    }

    override fun proceedToNextScreen(pokemonId: Long?) {
        fragmentHost?.proceedToPokemonScreen(pokemonId)
    }

    override fun updateFilterButtons(activateFilter: Boolean, filter: String) {
        val view = when (filter) {
            FilterData.FILTER_ATTACK -> filterAttack
            FilterData.FILTER_DEFENCE -> filterDefence
            FilterData.FILTER_SPEED -> filterSpeed
            else -> null
        }

        if (activateFilter) {
            view?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.filter_active))
        } else {
            view?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.filter_inactive))
        }
    }

    override fun onClick(view: View) {
        if (view === refresh) {
            showProgress()
        } else {
            when {
                filterAttack === view -> model?.sort(FilterData.FILTER_ATTACK)
                filterDefence === view -> model?.sort(FilterData.FILTER_DEFENCE)
                filterSpeed === view -> model?.sort(FilterData.FILTER_SPEED)
            }
        }
    }

    override fun onRefresh() {
        scrollListener.resetState()
        model?.reload()
    }

    override fun showProgress() {
        if (refreshSwipe?.isRefreshing == false) {
            refreshSwipe?.isRefreshing = true
        }
    }

    override fun hideProgress() {
        if (refreshSwipe?.isRefreshing == true) {
            refreshSwipe?.isRefreshing = false
        }
    }

    override fun setItems(list: List<PokemonFullDataSchema>) {
        adapter.setItems(list)
    }

    override fun scrollToStart() {
        recyclerView?.scrollToPosition(0)
    }

    override fun showError(error: Throwable?) {
        fragmentHost?.showError(error)
    }
}
