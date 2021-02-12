package com.mdgd.pokemon.bg

import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import kotlinx.coroutines.*

class LoadPokemonsModel(private val repo: PokemonsRepo, private val cache: Cache) : ServiceModel {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { ctx, e ->
        coroutineScope.launch {
            cache.putLoadingProgress(Result(e))

            cancelScope()
        }
    }

    override fun load() {
        coroutineScope.launch(exceptionHandler) {
            val initialAmount = PokemonsRepo.PAGE_SIZE.toLong() * 2
            repo.loadInitialPages(initialAmount)
            cache.putLoadingProgress(Result(initialAmount))

            cache.putLoadingProgress(Result(repo.loadPokemons(initialAmount)))
            cancelScope()
        }
    }

    private suspend fun cancelScope() {
        delay(50)
        coroutineScope.cancel()
    }
}
