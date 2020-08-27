package com.mdgd.pokemon.models.repo.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationViii {

    @SerializedName("icons")
    @Expose
    private Icons_ icons;

    public Icons_ getIcons() {
        return icons;
    }

    public void setIcons(Icons_ icons) {
        this.icons = icons;
    }

}
