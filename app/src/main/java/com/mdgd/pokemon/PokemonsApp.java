package com.mdgd.pokemon;

import android.app.Application;

import com.mdgd.pokemon.models.AppComponent;
import com.mdgd.pokemon.models.AppModule;
import com.mdgd.pokemon.models.DaggerAppComponent;

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
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
