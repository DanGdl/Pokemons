package com.mdgd.pokemon.models_impl.cache

import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class CacheImpl : Cache {
    private val progressChanel = MutableSharedFlow<Result<Long>>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun putLoadingProgress(value: Result<Long>) {
        progressChanel.tryEmit(value)
    }

    override fun getProgressChanel(): Flow<Result<Long>> {
        return progressChanel
    }
}
