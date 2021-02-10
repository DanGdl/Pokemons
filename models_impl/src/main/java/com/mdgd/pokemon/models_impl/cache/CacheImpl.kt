package com.mdgd.pokemon.models_impl.cache

import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class CacheImpl : Cache {
    private val progress = BehaviorSubject.createDefault(Result(0L))

    override fun putLoadingProgress(value: Result<Long>) {
        progress.onNext(value)
    }

    override fun getProgressObservable(): Observable<Result<Long>> {
        return progress
    }
}
