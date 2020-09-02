package com.mdgd.pokemon.models.repo.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ability_ {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("url")
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
