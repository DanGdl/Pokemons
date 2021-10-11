package com.mdgd.pokemon.models.cache

import com.mdgd.pokemon.models.infra.Result
import kotlinx.coroutines.flow.Flow

interface Cache {
    fun putLoadingProgress(value: Result<Long>)
    fun getProgressChanel(): Flow<Result<Long>>
}
