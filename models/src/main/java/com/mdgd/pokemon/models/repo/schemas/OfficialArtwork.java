package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfficialArtwork {

    @ColumnInfo(name = "OfficialArtworkFrontDefault")
    @Expose
    @SerializedName("front_default")
    private String frontDefault;

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

}
