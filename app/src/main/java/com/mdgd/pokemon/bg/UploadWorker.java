package com.mdgd.pokemon.bg;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {

    private final LoadPokemonsContract.ServiceModel model;

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        model = new PokemonsLoadingModelFactory().create();
    }

    @Override
    public Result doWork() {
        model.load();
        return Result.success();
    }
}

