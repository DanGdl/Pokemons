package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationI {

    @Embedded
    @Expose
    @SerializedName("red-blue")
    private RedBlue redBlue;

    @Embedded
    @Expose
    @SerializedName("yellow")
    private Yellow yellow;

    public RedBlue getRedBlue() {
        return redBlue;
    }

    public void setRedBlue(RedBlue redBlue) {
        this.redBlue = redBlue;
    }

    public Yellow getYellow() {
        return yellow;
    }

    public void setYellow(Yellow yellow) {
        this.yellow = yellow;
    }

}
