package com.mdgd.pokemon;

import android.app.Application;

import com.mdgd.pokemon.models.AppModule;
import com.mdgd.pokemon.models.DefaultAppModule;

public class PokemonsApp extends Application {

    private static PokemonsApp instance;
    private AppModule appComponent;

    public static PokemonsApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = new DefaultAppModule(this);
    }

    public AppModule getAppComponent() {
        return appComponent;
    }
}
