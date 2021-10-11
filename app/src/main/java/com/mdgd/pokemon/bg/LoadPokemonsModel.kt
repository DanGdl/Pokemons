package com.mdgd.pokemon.bg

import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import kotlinx.coroutines.*

class LoadPokemonsModel(private val repo: PokemonsRepo, private val cache: Cache) : ServiceModel {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        cache.putLoadingProgress(Result(e))
        coroutineScope.cancel()
    }

    override fun load() {
        coroutineScope.launch(exceptionHandler) {
            val initialAmount = PokemonsRepo.PAGE_SIZE.toLong() * 2
            repo.loadInitialPages(initialAmount)
            cache.putLoadingProgress(Result(initialAmount))

            val newAmount = repo.loadPokemons(initialAmount)
            cache.putLoadingProgress(Result(newAmount))
            coroutineScope.cancel()
        }
    }
}
