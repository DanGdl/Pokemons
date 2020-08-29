package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationVii {

    @Embedded
    @Expose
    @SerializedName("icons")
    private Icons icons;

    @Embedded
    @Expose
    @SerializedName("ultra-sun-ultra-moon")
    private UltraSunUltraMoon ultraSunUltraMoon;

    public Icons getIcons() {
        return icons;
    }

    public void setIcons(Icons icons) {
        this.icons = icons;
    }

    public UltraSunUltraMoon getUltraSunUltraMoon() {
        return ultraSunUltraMoon;
    }

    public void setUltraSunUltraMoon(UltraSunUltraMoon ultraSunUltraMoon) {
        this.ultraSunUltraMoon = ultraSunUltraMoon;
    }

}
