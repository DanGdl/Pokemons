package com.mdgd.pokemon.bg

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.repo.PokemonsRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted context: Context, @Assisted params: WorkerParameters,
    repo: PokemonsRepo, cache: Cache
) : Worker(context, params) {
    private val model: ServiceModel = LoadPokemonsModel(repo, cache)

    override fun doWork(): Result {
        model.load()
        return Result.success()
    }
}
