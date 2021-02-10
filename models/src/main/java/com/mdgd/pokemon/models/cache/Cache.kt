package com.mdgd.pokemon.models.cache

import com.mdgd.pokemon.models.infra.Result
import io.reactivex.rxjava3.core.Observable

interface Cache {
    fun putLoadingProgress(value: Result<Long>)
    fun getProgressObservable(): Observable<Result<Long>>
}
