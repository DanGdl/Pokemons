package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DreamWorld {

    @ColumnInfo(name = "DreamWorldFrontDefault")
    @Expose
    @SerializedName("front_default")
    private String frontDefault;

    @ColumnInfo(name = "DreamWorldFrontFemale")
    @Expose
    @SerializedName("front_female")
    private String frontFemale;

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontFemale() {
        return frontFemale;
    }

    public void setFrontFemale(String frontFemale) {
        this.frontFemale = frontFemale;
    }

}
