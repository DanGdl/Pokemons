package com.mdgd.pokemon.ui.pokemons

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.compose.rememberImagePainter
import com.google.android.material.composethemeadapter.MdcTheme
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.PokemonsApp
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.filters.FilterData
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.schemas.Stat
import com.mdgd.pokemon.models.repo.schemas.Stat_
import com.mdgd.pokemon.ui.adapter.ClickEvent
import com.mdgd.pokemon.ui.error.ErrorParams
import com.mdgd.pokemon.ui.error.ErrorScreen
import com.mdgd.pokemon.ui.pokemons.adapter.PokemonsAdapter
import com.mdgd.pokemon.ui.pokemons.infra.EndlessScrollListener
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenAction
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokemonsFragment : HostedFragment<
        PokemonsContract.View,
        PokemonsScreenState,
        PokemonsScreenAction,
        PokemonsContract.ViewModel,
        PokemonsContract.Host>(),
    PokemonsContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val screenState = mutableStateOf(PokemonsUiState())

    private val adapter = PokemonsAdapter(lifecycleScope)
    private var refreshSwipe: SwipeRefreshLayout? = null

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
            adapter.getItemClickFlow().collect {
                if (it is ClickEvent.ClickData) {
                    model!!.onItemClicked(it.data)
                }
            }
        }
    }

    override fun createModel(): PokemonsContract.ViewModel {
        return ViewModelProvider(
            this,
            PokemonsViewModelFactory(PokemonsApp.instance?.appComponent!!)
        ).get(PokemonsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        view.setContent {
            PokemonsScreen(screenState, model)
        }
        return view // inflater.inflate(R.layout.fragment_pokemons, container, false)
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
            if (!refreshSwipe!!.isRefreshing) {
                refreshSwipe!!.isRefreshing = true
            }
        } else {
            when {
                filterAttack === view -> {
                    model!!.sort(FilterData.FILTER_ATTACK)
                }
                filterDefence === view -> {
                    model!!.sort(FilterData.FILTER_DEFENCE)
                }
                filterSpeed === view -> {
                    model!!.sort(FilterData.FILTER_SPEED)
                }
            }
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
        screenState.value = screenState.value.copy(
            isLoading = false, pokemons = list, isVisible = false
        )
    }

    override fun scrollToStart() {
        recyclerView!!.scrollToPosition(0)
    }

    override fun showError(error: Throwable?) {
        screenState.value = screenState.value.copy(
            isLoading = false, isVisible = true, title = getString(R.string.dialog_error_title),
            message = error?.let {
                getString(R.string.dialog_error_message) + " " + error.message
            } ?: kotlin.run {
                getString(R.string.dialog_error_message)
            })
    }
}


@Composable
fun PokemonsScreen(screenState: MutableState<PokemonsUiState>, model: PokemonsContract.ViewModel?) {
    val errorDialogTrigger = remember { screenState as MutableState<ErrorParams> }
    MdcTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { model?.reload() },
                    modifier = Modifier.padding(0.dp, 50.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_refresh),
                        contentDescription = stringResource(R.string.screen_pokemons_refresh)
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.94F)
                ) {
                    items(
                        items = screenState.value.pokemons,
                        key = { item ->
                            item.pokemonSchema?.id ?: 0L
                        }
                    ) { item ->
                        PokemonItem(item, model)
                    }
                }
                BottomBar(screenState, model)
                ErrorScreen(errorDialogTrigger)
            }
        }
    }
}


@Composable
fun PokemonItem(item: PokemonFullDataSchema, model: PokemonsContract.ViewModel?) {
    var attackVal = "--"
    var defenceVal = "--"
    var speedVal = "--"
    for (s in item.stats) {
        s.stat?.name?.let {
            when (s.stat!!.name) {
                "defense" -> defenceVal = s.baseStat.toString()
                "speed" -> speedVal = s.baseStat.toString()
                "attack" -> attackVal = s.baseStat.toString()
            }
        }
    }
    attackVal = LocalContext.current.getString(R.string.item_pokemon_defence, defenceVal)
    defenceVal = LocalContext.current.getString(R.string.item_pokemon_attack, attackVal)
    speedVal = LocalContext.current.getString(R.string.item_pokemon_speed, speedVal)
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(3.dp),
        modifier = Modifier
//            .clickable(onClick = { Log.d("LOGG", "Logg") /*model.onItemClicked(item)*/ },)
            .background(color = Color.Cyan)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = item.pokemonSchema?.sprites?.other?.officialArtwork?.frontDefault?.let {
                    rememberImagePainter(
                        data = it // TODO: catch fail
                    )
                } ?: kotlin.run {
                    painterResource(R.drawable.logo_splash)
                },
                contentDescription = stringResource(id = R.string.screen_pokemons_icon),
                modifier = Modifier
                    .fillMaxWidth(0.3F)
                    .aspectRatio(1F),
                contentScale = ContentScale.Inside
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = item.pokemonSchema?.name ?: "",
                    style = TextStyle(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxHeight(0.4F),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = attackVal,
                        style = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier.weight(1F),
                        maxLines = 2
                    )
                    Text(
                        text = defenceVal,
                        style = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier.weight(1F),
                        maxLines = 2
                    )
                    Text(
                        text = speedVal,
                        style = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier.weight(1F),
                        maxLines = 2
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(screenState: MutableState<PokemonsUiState>, model: PokemonsContract.ViewModel?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { model?.sort(FilterData.FILTER_ATTACK) },
            modifier = Modifier.weight(1F),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_attack),
                contentDescription = stringResource(R.string.screen_filter_attack),
                tint = colorResource(
                    id = if (screenState.value.isAttackActive) {
                        R.color.filter_active
                    } else {
                        R.color.filter_inactive
                    }
                )
            )
        }
        IconButton(
            onClick = { model?.sort(FilterData.FILTER_DEFENCE) },
            modifier = Modifier.weight(1F),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_defense),
                contentDescription = stringResource(R.string.screen_filter_defence),
                modifier = Modifier.weight(1F),
                tint = colorResource(
                    id = if (screenState.value.isDefenceActive) {
                        R.color.filter_active
                    } else {
                        R.color.filter_inactive
                    }
                ),
            )
        }
        IconButton(
            onClick = { model?.sort(FilterData.FILTER_SPEED) },
            modifier = Modifier.weight(1F),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_speed),
                contentDescription = stringResource(R.string.screen_filter_speed),
                modifier = Modifier.weight(1F),
                tint = colorResource(
                    id = if (screenState.value.isSpeedActive) {
                        R.color.filter_active
                    } else {
                        R.color.filter_inactive
                    }
                ),
            )
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PokemonItemPreviewThemeDark() {
    val pokemon = PokemonFullDataSchema()
    pokemon.pokemonSchema = PokemonSchema()
    pokemon.pokemonSchema?.name = "SlowPock"
    pokemon.stats = mutableListOf()

    val attack = Stat()
    attack.stat = Stat_()
    attack.stat?.name = "attack"
    attack.baseStat = 100500
    pokemon.stats.add(attack)

    val defence = Stat()
    defence.stat = Stat_()
    defence.stat?.name = "defense"
    defence.baseStat = 100501
    pokemon.stats.add(defence)

    val speed = Stat()
    speed.stat = Stat_()
    speed.stat?.name = "speed"
    speed.baseStat = 100502
    pokemon.stats.add(speed)

    MdcTheme {
        PokemonItem(pokemon, null)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun PokemonsPreviewThemeLight() {
    val pokemon = PokemonFullDataSchema()
    pokemon.pokemonSchema = PokemonSchema()
    pokemon.pokemonSchema?.name = "SlowPock"
    pokemon.stats = mutableListOf()

    val attack = Stat()
    attack.stat = Stat_()
    attack.stat?.name = "attack"
    attack.baseStat = 100500
    pokemon.stats.add(attack)

    val defence = Stat()
    defence.stat = Stat_()
    defence.stat?.name = "defense"
    defence.baseStat = 100501
    pokemon.stats.add(defence)

    val speed = Stat()
    speed.stat = Stat_()
    speed.stat?.name = "speed"
    speed.baseStat = 100502
    pokemon.stats.add(speed)

    MdcTheme {
        PokemonsScreen(mutableStateOf(PokemonsUiState(pokemons = listOf(pokemon))), null)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PokemonsPreviewThemeDark() {
    MdcTheme {
        PokemonsScreen(mutableStateOf(PokemonsUiState()), null)
    }
}
