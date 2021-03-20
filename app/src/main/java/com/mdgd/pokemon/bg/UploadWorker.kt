package com.mdgd.pokemon.bg

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mdgd.pokemon.PokemonsApp.Companion.instance

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val model: ServiceModel = PokemonsLoadingModelFactory(instance!!.appComponent!!).create()

    override fun doWork(): Result {
        model.load()
        return Result.success()
    }
}
