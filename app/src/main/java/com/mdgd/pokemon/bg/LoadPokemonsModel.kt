package com.mdgd.pokemon.bg

import android.util.Log
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import kotlinx.coroutines.*

class LoadPokemonsModel(private val repo: PokemonsRepo, private val cache: Cache) : ServiceModel {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { ctx, e ->
        Log.d("LOGG", "Got error ${e.message}");
        coroutineScope.launch {
            Log.d("LOGG", "Handle error");
            cache.putLoadingProgress(Result(e))

            cancelScope()
        }
    }

    override fun load() {
        coroutineScope.launch(exceptionHandler) {
            val initialAmount = PokemonsRepo.PAGE_SIZE.toLong() * 2
            Log.d("LOGG", "Call repo.loadInitialPages()");
            repo.loadInitialPages(initialAmount)

            Log.d("LOGG", "Got result ofrepo.loadInitialPages()");
            cache.putLoadingProgress(Result(initialAmount))

            Log.d("LOGG", "Call repo.loadPokemons_S()");
            val count = repo.loadPokemons_S(initialAmount)
            cache.putLoadingProgress(Result(count))
            Log.d("LOGG", "Got result of.loadPokemons_S()");
            cancelScope()
        }
    }

    private suspend fun cancelScope() {
        Log.d("LOGG", "cancelScope");
        delay(50)
        coroutineScope.cancel()
    }
}
