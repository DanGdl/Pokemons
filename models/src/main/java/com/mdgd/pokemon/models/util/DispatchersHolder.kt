package com.mdgd.pokemon.models.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {
    fun getMain(): CoroutineDispatcher

    fun getIO(): CoroutineDispatcher
}
