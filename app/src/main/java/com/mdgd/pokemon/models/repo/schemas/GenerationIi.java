package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationIi {

    @Embedded
    @Expose
    @SerializedName("crystal")
    private Crystal crystal;

    @Embedded
    @Expose
    @SerializedName("gold")
    private Gold gold;

    @Embedded
    @Expose
    @SerializedName("silver")
    private Silver silver;

    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }

    public Gold getGold() {
        return gold;
    }

    public void setGold(Gold gold) {
        this.gold = gold;
    }

    public Silver getSilver() {
        return silver;
    }

    public void setSilver(Silver silver) {
        this.silver = silver;
    }

}
