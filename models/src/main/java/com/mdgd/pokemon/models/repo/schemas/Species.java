package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Species {

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "species_name")
    private String name;

    @Expose
    @SerializedName("url")
    @ColumnInfo(name = "species_url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
