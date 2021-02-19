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
import com.mdgd.mvi.HostedFragment
import com.mdgd.pokemon.PokemonsApp
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.adapter.PokemonsAdapter
import com.mdgd.pokemon.ui.pokemons.infra.FilterData
import com.mdgd.pokemon.ui.pokemons.infra.ui.ClickEvent
import com.mdgd.pokemon.ui.pokemons.infra.ui.EndlessScrollListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class PokemonsFragment : HostedFragment<
        PokemonsContract.View,
        PokemonsScreenState,
        PokemonsContract.ViewModel,
        PokemonsContract.Host>(),
        PokemonsContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val filters: MutableList<String> = ArrayList(3)
    private val adapter = PokemonsAdapter(lifecycleScope)
    private var refreshSwipe: SwipeRefreshLayout? = null

    companion object {
        fun newInstance(): PokemonsFragment {
            return PokemonsFragment()
        }
    }

    // maybe paging library?
    private val scrollListener: EndlessScrollListener = object : EndlessScrollListener() {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            model!!.loadPage(page)
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
            adapter.onItemClickSubject.collect { pokemon ->
                if (pokemon is ClickEvent.ClickData)
                    model!!.onItemClicked(pokemon.data)
            }
        }
    }

    override fun createModel(): PokemonsContract.ViewModel {
        return ViewModelProvider(this, PokemonsViewModelFactory(PokemonsApp.instance?.appComponent!!)).get(PokemonsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        if (hasHost()) {
            fragmentHost!!.proceedToPokemonScreen(pokemonId)
        }
    }

    override fun onClick(view: View) {
        if (view === refresh) {
            if (!refreshSwipe!!.isRefreshing) {
                refreshSwipe!!.isRefreshing = true
            }
        } else {
            when {
                filterAttack === view -> {
                    setupFilter(filterAttack, FilterData.FILTER_ATTACK)
                }
                filterDefence === view -> {
                    setupFilter(filterDefence, FilterData.FILTER_DEFENCE)
                }
                filterSpeed === view -> {
                    setupFilter(filterSpeed, FilterData.FILTER_SPEED)
                }
            }
            model!!.sort(FilterData(ArrayList(filters)))
        }
    }

    private fun setupFilter(filterView: ImageButton?, filterTag: String) {
        if (filters.contains(filterTag)) {
            filters.remove(filterTag)
            filterView!!.setColorFilter(ContextCompat.getColor(requireContext(), R.color.filter_inactive))
        } else {
            filters.add(filterTag)
            filterView!!.setColorFilter(ContextCompat.getColor(requireContext(), R.color.filter_active))
        }
    }

    override fun onRefresh() {
        scrollListener.resetState()
        model!!.reload()
    }

    override fun showProgress() {
        if (refreshSwipe != null && !refreshSwipe!!.isRefreshing) {
            refreshSwipe!!.isRefreshing = true
        }
    }

    override fun hideProgress() {
        if (refreshSwipe != null && refreshSwipe!!.isRefreshing) {
            refreshSwipe!!.isRefreshing = false
        }
    }

    override fun setItems(list: List<PokemonFullDataSchema>) {
        adapter.setItems(list)
    }

    override fun updateItems(list: List<PokemonFullDataSchema>) {
        adapter.updateItems(list)
        recyclerView!!.scrollToPosition(0)
    }

    override fun showError(error: Throwable?) {
        if (hasHost()) {
            fragmentHost!!.showError(error)
        }
    }
}