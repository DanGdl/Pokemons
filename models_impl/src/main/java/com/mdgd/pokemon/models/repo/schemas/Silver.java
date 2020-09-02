package com.mdgd.pokemon.models.repo.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Silver {

    @Expose
    @SerializedName("back_default")
    private String backDefault;

    @Expose
    @SerializedName("back_shiny")
    private String backShiny;

    @Expose
    @SerializedName("front_default")
    private String frontDefault;

    @Expose
    @SerializedName("front_shiny")
    private String frontShiny;

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

}
