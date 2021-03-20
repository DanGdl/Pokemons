package com.mdgd.pokemon.models.cache

import com.mdgd.pokemon.models.infra.Result
import kotlinx.coroutines.channels.Channel

interface Cache {
    suspend fun putLoadingProgress(value: Result<Long>)
    fun getProgressChanel(): Channel<Result<Long>>
}
