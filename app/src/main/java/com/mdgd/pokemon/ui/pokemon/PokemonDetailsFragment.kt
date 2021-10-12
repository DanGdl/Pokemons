package com.mdgd.pokemon.ui.pokemon

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.google.android.material.composethemeadapter.MdcTheme
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.PokemonsApp.Companion.instance
import com.mdgd.pokemon.R
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonSchema
import com.mdgd.pokemon.models.repo.schemas.Stat
import com.mdgd.pokemon.models.repo.schemas.Stat_
import com.mdgd.pokemon.ui.error.ErrorParams
import com.mdgd.pokemon.ui.error.ErrorScreen
import com.mdgd.pokemon.ui.pokemon.dto.*
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenAction
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState

class PokemonDetailsFragment : HostedFragment<
        PokemonDetailsContract.View,
        PokemonDetailsScreenState,
        PokemonDetailsScreenAction,
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
        return ViewModelProvider(
            this,
            PokemonDetailsViewModelFactory(instance!!.appComponent!!)
        ).get(PokemonDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = ComposeView(requireContext())
        view.setContent {
            PokemonScreen(screenState)
        }
        return view
    }

    override fun setItems(items: List<PokemonProperty>) {
        screenState.value = screenState.value.copy(properties = items)
    }
}

@Composable
fun PokemonScreen(screenState: MutableState<PokemonUiState>) {
    val errorDialogTrigger = remember { screenState as MutableState<ErrorParams> }
    MdcTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
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
            Image(
                contentDescription = stringResource(id = R.string.fragment_pokemon_picture),
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(200.dp),
                painter = rememberImagePainter(p.imageUrl), // TODO: catch error
            )
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
    MdcTheme {
        PokemonScreen(mutableStateOf(PokemonUiState(properties = listOf())))
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Pokemon Dark Mode"
)
@Composable
fun PokemonPreviewThemeDark() {
//    val properties: MutableList<PokemonProperty> = ArrayList()
//    val pokemonSchema = pokemonDetails.pokemonSchema
//    properties.add(ImagePropertyData(pokemonSchema!!.sprites!!.other!!.officialArtwork!!.frontDefault!!))
//    properties.add(LabelPropertyData(R.string.pokemon_detail_name, pokemonSchema.name!!))
//    properties.add(LabelPropertyData(R.string.pokemon_detail_height, pokemonSchema.height.toString()))
//    properties.add(LabelPropertyData(R.string.pokemon_detail_weight, pokemonSchema.weight.toString()))
//    properties.add(TitlePropertyData(R.string.pokemon_detail_stats))
//    for (s in pokemonDetails.stats) {
//        properties.add(LabelPropertyData(s.stat!!.name!!, s.baseStat.toString(), 1))
//    }
//    properties.add(TitlePropertyData(R.string.pokemon_detail_abilities))
//    val abilities: List<Ability> = pokemonDetails.abilities
//    val abilitiesText = StringBuilder()
//    for (i in abilities.indices) {
//        abilitiesText.append(abilities[i].ability!!.name)
//        if (i < abilities.size - 1) {
//            abilitiesText.append(", ")
//        }
//    }
//    properties.add(TextPropertyData(abilitiesText.toString(), 1))
//    properties.add(TitlePropertyData(R.string.pokemon_detail_forms))
//    val forms: List<Form> = pokemonDetails.forms
//    val formsText = StringBuilder()
//    for (i in forms.indices) {
//        formsText.append(forms[i].name)
//        if (i < forms.size - 1) {
//            formsText.append(", ")
//        }
//    }
//    properties.add(TextPropertyData(formsText.toString(), 1))
//    properties.add(TitlePropertyData(R.string.pokemon_detail_types))
//    val types: List<Type> = pokemonDetails.types
//    val typesText = StringBuilder()
//    for (i in types.indices) {
//        typesText.append(types[i].type!!.name)
//        if (i < types.size - 1) {
//            typesText.append(", ")
//        }
//    }
//    properties.add(TextPropertyData(typesText.toString(), 1))
//    properties.add(TitlePropertyData(R.string.pokemon_detail_game_indicies))
//    val gameIndices: List<GameIndex> = pokemonDetails.gameIndices
//    val gameIndicesText = StringBuilder()
//    for (i in gameIndices.indices) {
//        gameIndicesText.append(gameIndices[i].version!!.name)
//        if (i < gameIndices.size - 1) {
//            gameIndicesText.append(", ")
//        }
//    }
//    properties.add(TextPropertyData(gameIndicesText.toString(), 1))
    MdcTheme {
        PokemonScreen(mutableStateOf(PokemonUiState(properties = listOf())))
    }
}
