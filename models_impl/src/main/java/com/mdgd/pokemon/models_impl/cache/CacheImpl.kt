package com.mdgd.pokemon.models_impl.cache

import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import kotlinx.coroutines.channels.Channel

class CacheImpl : Cache {
    private val progressChanel = Channel<Result<Long>>()

    override suspend fun putLoadingProgress(value: Result<Long>) {
        progressChanel.send(value)
    }

    override fun getProgressChanel(): Channel<Result<Long>> {
        return progressChanel
    }
}
