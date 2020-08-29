package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationViii {

    @Embedded
    @Expose
    @SerializedName("icons")
    private Icons_ icons;

    public Icons_ getIcons() {
        return icons;
    }

    public void setIcons(Icons_ icons) {
        this.icons = icons;
    }

}
