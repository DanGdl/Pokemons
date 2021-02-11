package com.mdgd.pokemon;

import androidx.multidex.MultiDexApplication;

import com.mdgd.pokemon.models.AppModule;
import com.mdgd.pokemon.models_impl.DefaultAppModule;

public class PokemonsApp extends MultiDexApplication {

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
