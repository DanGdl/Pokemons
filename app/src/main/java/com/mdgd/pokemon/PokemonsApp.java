package com.mdgd.pokemon;

import android.app.Application;

import com.mdgd.pokemon.models.AppComponent;
import com.mdgd.pokemon.models.DaggerAppComponent;
import com.mdgd.pokemon.models.DefaultAppModule;

public class PokemonsApp extends Application {

    private static PokemonsApp instance;
    private AppComponent appComponent;

    public static PokemonsApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new DefaultAppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
