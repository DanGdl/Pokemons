package com.mdgd.pokemon.models_impl.repo

import com.mdgd.pokemon.models.repo.PokemonsRepo
import com.mdgd.pokemon.models.repo.cache.PokemonsCache
import com.mdgd.pokemon.models.repo.dao.PokemonsDao
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.Network
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import com.mdgd.pokemon.models_impl.Mocks
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.*

@RunWith(JUnit4::class)
class PokemonsRepositoryTest {

    private lateinit var repo: PokemonsRepository
    private lateinit var cache: PokemonsCache
    private lateinit var network: Network
    private lateinit var dao: PokemonsDao

    @Before
    fun setUp() {
        dao = Mockito.mock(PokemonsDao::class.java)
        network = Mockito.mock(Network::class.java)
        cache = Mockito.mock(PokemonsCache::class.java)
        repo = PokemonsRepository(dao, network, cache)
    }

    private fun verifyNoMore() {
        Mockito.verifyNoMoreInteractions(cache)
        Mockito.verifyNoMoreInteractions(network)
        Mockito.verifyNoMoreInteractions(dao)
    }

    @Test
    fun test_getCachedPokemons() {
        val list = ArrayList<PokemonFullDataSchema>()
        Mockito.`when`(cache.getPokemons()).thenReturn(list)

        val pokemons = repo.getPokemons()

        Mockito.verify(cache, Mockito.times(1)).getPokemons()
        Assert.assertEquals(list, pokemons)
        verifyNoMore()
    }

    @Test
    fun test_getPageFromDao_InitialPage() = runTest {
        val list = listOf(Mocks.getPokemon())
        Mockito.`when`(dao.getPage(0, PokemonsRepo.PAGE_SIZE)).thenReturn(list)

        val pokemons = repo.getPage(0)

        Mockito.verify(dao, Mockito.times(1)).getPage(0, PokemonsRepo.PAGE_SIZE)
        Mockito.verify(cache, Mockito.times(1)).setPokemons(list)
        Assert.assertEquals(list, pokemons)
        verifyNoMore()
    }

    @Test
    fun test_getPageFromDao() = runTest {
        val list = listOf(Mocks.getPokemon())
        Mockito.`when`(dao.getPage(1, PokemonsRepo.PAGE_SIZE)).thenReturn(list)

        val pokemons = repo.getPage(1)

        Mockito.verify(dao, Mockito.times(1)).getPage(1, PokemonsRepo.PAGE_SIZE)
        Mockito.verify(cache, Mockito.times(1)).addPokemons(list)
        Assert.assertEquals(list, pokemons)
        verifyNoMore()
    }

    @Test
    fun test_getPageFromNetwork() = runTest {
        val emptyList = listOf<PokemonFullDataSchema>()
        val list = listOf(Mocks.getPokemon())
        val networkList = listOf<PokemonDetails>()

        val invocations = Array(1) { 0 }
        Mockito.`when`(dao.getPage(1, PokemonsRepo.PAGE_SIZE)).then {
            invocations[0]++
            if (invocations[0] == 1) {
                emptyList
            } else {
                list
            }
        }
        Mockito.`when`(network.loadPokemons(1, PokemonsRepo.PAGE_SIZE)).thenReturn(networkList)

        val pokemons = repo.getPage(1)

        Mockito.verify(network, Mockito.times(1)).loadPokemons(1, PokemonsRepo.PAGE_SIZE)
        Mockito.verify(dao, Mockito.times(2)).getPage(1, PokemonsRepo.PAGE_SIZE)
        Mockito.verify(dao, Mockito.times(1)).save(networkList)
        Mockito.verify(cache, Mockito.times(1)).addPokemons(list)
        Assert.assertEquals(list, pokemons)
        verifyNoMore()
    }

    @Test
    fun test_loadPokemons_AllLoaded() = runTest {
        val initialAmount = 10L
        Mockito.`when`(dao.getCount()).thenReturn(initialAmount)
        Mockito.`when`(network.getPokemonsCount()).thenReturn(initialAmount)

        val amount = repo.loadPokemons(initialAmount)

        Mockito.verify(network, Mockito.times(1)).getPokemonsCount()
        Mockito.verify(dao, Mockito.times(1)).getCount()
        Assert.assertEquals(initialAmount, amount)
        verifyNoMore()
    }

    @Test
    fun test_loadPokemons_NothingLoaded() = runTest {
        val initialAmount = 10L
        val totalAmount = 20L
        val networkList =
            ArrayList(Collections.nCopies(totalAmount.toInt(), Mocks.getPokemonDetails()))
        Mockito.`when`(dao.getCount()).thenReturn(initialAmount)
        Mockito.`when`(network.getPokemonsCount()).thenReturn(totalAmount)
        Mockito.`when`(network.loadPokemons(totalAmount, initialAmount)).thenReturn(networkList)

        val amount = repo.loadPokemons(initialAmount)

        Mockito.verify(network, Mockito.times(1)).getPokemonsCount()
        Mockito.verify(network, Mockito.times(1)).loadPokemons(totalAmount, initialAmount)
        Mockito.verify(dao, Mockito.times(1)).getCount()
        Mockito.verify(dao, Mockito.times(1)).save(networkList)
        Assert.assertEquals(totalAmount, amount)
        verifyNoMore()
    }

    @Test
    fun test_loadInitialPages_NoData() = runTest {
        val initialAmount = 10L
        val networkList =
            ArrayList(Collections.nCopies(initialAmount.toInt(), Mocks.getPokemonDetails()))
        Mockito.`when`(dao.getCount()).thenReturn(0)
        Mockito.`when`(network.loadPokemons(initialAmount, 0)).thenReturn(networkList)

        repo.loadInitialPages(initialAmount)

        Mockito.verify(network, Mockito.times(1)).loadPokemons(initialAmount, 0)
        Mockito.verify(dao, Mockito.times(1)).getCount()
        Mockito.verify(dao, Mockito.times(1)).save(networkList)
        verifyNoMore()
    }

    @Test
    fun test_loadInitialPages() = runTest {
        val initialAmount = 10L
        Mockito.`when`(dao.getCount()).thenReturn(initialAmount)

        repo.loadInitialPages(initialAmount)

        Mockito.verify(dao, Mockito.times(1)).getCount()
        verifyNoMore()
    }

    @Test
    fun test_getPokemonById_Cached() = runTest {
        val pokemonId = 0L
        val pokemon = Mocks.getPokemon()
        pokemon.pokemonSchema?.id = pokemonId
        Mockito.`when`(cache.getPokemons()).thenReturn(listOf(pokemon))

        val pokemonById = repo.getPokemonById(pokemonId)

        Mockito.verify(cache, Mockito.times(1)).getPokemons()
        Assert.assertEquals(pokemon, pokemonById)
        verifyNoMore()
    }

    @Test
    fun test_getPokemonById_NotCached() = runTest {
        val pokemonId = 0L
        val pokemon = Mocks.getPokemon()
        Mockito.`when`(dao.getPokemonById(pokemonId)).thenReturn(pokemon)
        Mockito.`when`(cache.getPokemons()).thenReturn(listOf(pokemon))

        val pokemonById = repo.getPokemonById(pokemonId)

        Mockito.verify(dao, Mockito.times(1)).getPokemonById(pokemonId)
        Mockito.verify(cache, Mockito.times(1)).getPokemons()
        Assert.assertEquals(pokemon, pokemonById)
        verifyNoMore()
    }
}
