package com.mdgd.pokemon.models.repo.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedBlue {

    @SerializedName("back_default")
    @Expose
    private Object backDefault;
    @SerializedName("back_gray")
    @Expose
    private Object backGray;
    @SerializedName("front_default")
    @Expose
    private Object frontDefault;
    @SerializedName("front_gray")
    @Expose
    private Object frontGray;

    public Object getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(Object backDefault) {
        this.backDefault = backDefault;
    }

    public Object getBackGray() {
        return backGray;
    }

    public void setBackGray(Object backGray) {
        this.backGray = backGray;
    }

    public Object getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(Object frontDefault) {
        this.frontDefault = frontDefault;
    }

    public Object getFrontGray() {
        return frontGray;
    }

    public void setFrontGray(Object frontGray) {
        this.frontGray = frontGray;
    }

}
