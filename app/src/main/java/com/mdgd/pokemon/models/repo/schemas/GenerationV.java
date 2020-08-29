package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationV {

    @Embedded
    @Expose
    @SerializedName("black-white")
    private BlackWhite blackWhite;

    public BlackWhite getBlackWhite() {
        return blackWhite;
    }

    public void setBlackWhite(BlackWhite blackWhite) {
        this.blackWhite = blackWhite;
    }

}
