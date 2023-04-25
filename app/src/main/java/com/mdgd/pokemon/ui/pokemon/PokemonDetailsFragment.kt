package com.mdgd.pokemon.ui.pokemon

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.schemas.Stat
import com.mdgd.pokemon.models.repo.schemas.Stat_
import com.mdgd.pokemon.ui.error.ErrorParams
import com.mdgd.pokemon.ui.error.ErrorScreen
import com.mdgd.pokemon.ui.pokemon.dto.ImageProperty
import com.mdgd.pokemon.ui.pokemon.dto.ImagePropertyData
import com.mdgd.pokemon.ui.pokemon.dto.LabelProperty
import com.mdgd.pokemon.ui.pokemon.dto.LabelPropertyData
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty
import com.mdgd.pokemon.ui.pokemon.dto.TextProperty
import com.mdgd.pokemon.ui.pokemon.dto.TextPropertyData
import com.mdgd.pokemon.ui.pokemon.dto.TitleProperty
import com.mdgd.pokemon.ui.pokemon.dto.TitlePropertyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : HostedFragment<
        PokemonDetailsContract.View,
        PokemonDetailsContract.ViewModel,
        PokemonDetailsContract.Host>(), PokemonDetailsContract.View {

    private val screenState = mutableStateOf(PokemonUiState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model?.setPokemonId(PokemonDetailsFragmentArgs.fromBundle(requireArguments()).pokemonId)
        }
    }

    override fun createModel(): PokemonDetailsContract.ViewModel {
        val model: PokemonDetailsViewModel by viewModels()
        return model
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        view.setContent {
            PokemonScreen(screenState, model)
        }
        return view
    }

    override fun setItems(items: List<PokemonProperty>) {
        screenState.value = screenState.value.copy(properties = items)
    }

    override fun goBack() {
        fragmentHost?.onBackPressed()
    }
}

@Composable
fun PokemonScreen(
    screenState: MutableState<PokemonUiState>,
    model: PokemonDetailsContract.ViewModel?
) {
    val errorDialogTrigger = remember { screenState as MutableState<ErrorParams> }
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = { model?.onBackPressed() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.button_back)
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    if (screenState.value.properties.isNullOrEmpty()) {
                        items(items = listOf(System.currentTimeMillis()), key = { it }) {
                            Column(
                                modifier = Modifier
                                    .fillParentMaxHeight()
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = stringResource(id = R.string.no_pokemons),
                                )
                            }
                        }
                    } else {
                        items(items = screenState.value.properties) { PokemonDetailItem(it) }
                    }
                }
                ErrorScreen(errorDialogTrigger)
            }
        }
    }
}

@Composable
fun PokemonDetailItem(property: PokemonProperty) {
    when (property.type) {
        PokemonProperty.PROPERTY_IMAGE -> {
            val p = property as ImageProperty
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    contentDescription = stringResource(id = R.string.fragment_pokemon_picture),
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.size(200.dp),
                    painter = rememberImagePainter(
                        data = p.imageUrl,
                        builder = {
                            ImageRequest.Builder(LocalContext.current)
                                .placeholder(R.drawable.ic_pokemon)
                                .error(R.drawable.ic_pokemon)
                                .build()
                        }
                    ))
            }
        }
        PokemonProperty.PROPERTY_LABEL -> {
            val startPadding = dimensionResource(id = R.dimen.pokemon_details_nesting_level_padding)
            val p = property as LabelProperty

            val title = if (p.titleResId == 0) {
                p.titleStr
            } else {
                stringResource(id = p.titleResId)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .weight(1F)
                        .padding(
                            PaddingValues(
                                startPadding * (1 + p.nestingLevel),
                                0.dp, 0.dp, 0.dp
                            )
                        )
                )
                Text(
                    text = p.text,
                    fontSize = 16.sp,
                    style = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .weight(1F)
                        .padding(5.dp)
                )
            }
        }
        PokemonProperty.PROPERTY_TEXT -> {
            val startPadding = dimensionResource(id = R.dimen.pokemon_details_nesting_level_padding)
            val p = property as TextProperty
            Text(
                text = p.text,
                modifier = Modifier
                    .padding(PaddingValues(startPadding * (2 + p.nestingLevel), 5.dp, 5.dp, 5.dp))
                    .fillMaxWidth()
            )
        }
        PokemonProperty.PROPERTY_TITLE -> {
            val startPadding = dimensionResource(id = R.dimen.pokemon_details_nesting_level_padding)
            val p = property as TitleProperty

            val params = if (p.nestingLevel == 0) {
                Pair(
                    TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Default
                    ),
                    Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                )
            } else {
                Pair(
                    TextStyle(
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif
                    ),
                    Modifier
                        .padding(
                            PaddingValues(
                                startPadding * (2 + p.nestingLevel),
                                5.dp, 5.dp, 5.dp
                            )
                        )
                        .fillMaxWidth()
                )
            }
            Text(
                text = if (p.titleResId == 0) {
                    ""
                } else {
                    stringResource(id = p.titleResId)
                },
                fontSize = 18.sp,
                style = params.first,
                modifier = params.second
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Pokemon Light Mode"
)
@Composable
fun PokemonPreviewThemeLight() {
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
    MaterialTheme {
        val state: MutableState<PokemonUiState> = remember {
            mutableStateOf(PokemonUiState(properties = listOf()))
        }
        PokemonScreen(state, null)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Pokemon Dark Mode"
)
@Composable
fun PokemonPreviewThemeDark() {
    val properties: MutableList<PokemonProperty> = ArrayList()
    properties.add(ImagePropertyData("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/154.png"))
    properties.add(LabelPropertyData(R.string.pokemon_detail_name, "SlowPock"))
    properties.add(LabelPropertyData(R.string.pokemon_detail_height, "100"))
    properties.add(LabelPropertyData(R.string.pokemon_detail_weight, "90"))
    properties.add(TitlePropertyData(R.string.pokemon_detail_stats))
    properties.add(LabelPropertyData("Speed", "50", 1))
    properties.add(TitlePropertyData(R.string.pokemon_detail_abilities))
    properties.add(TextPropertyData("wololo, wololo", 1))
    properties.add(TitlePropertyData(R.string.pokemon_detail_forms))
    properties.add(TextPropertyData("wololo, wololo", 1))
    properties.add(TitlePropertyData(R.string.pokemon_detail_types))
    properties.add(TextPropertyData("bro, king", 1))
    properties.add(TitlePropertyData(R.string.pokemon_detail_game_indicies))
    properties.add(TextPropertyData("some indicies here", 1))
    MaterialTheme {
        val state: MutableState<PokemonUiState> = remember {
            mutableStateOf(PokemonUiState(properties = properties))
        }
        PokemonScreen(state, null)
    }
}
