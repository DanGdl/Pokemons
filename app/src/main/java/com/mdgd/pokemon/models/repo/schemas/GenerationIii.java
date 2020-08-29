package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationIii {

    @Embedded
    @Expose
    @SerializedName("emerald")
    private Emerald emerald;

    @Embedded
    @Expose
    @SerializedName("firered-leafgreen")
    private FireredLeafgreen fireredLeafgreen;

    @Embedded
    @Expose
    @SerializedName("ruby-sapphire")
    private RubySapphire rubySapphire;

    public Emerald getEmerald() {
        return emerald;
    }

    public void setEmerald(Emerald emerald) {
        this.emerald = emerald;
    }

    public FireredLeafgreen getFireredLeafgreen() {
        return fireredLeafgreen;
    }

    public void setFireredLeafgreen(FireredLeafgreen fireredLeafgreen) {
        this.fireredLeafgreen = fireredLeafgreen;
    }

    public RubySapphire getRubySapphire() {
        return rubySapphire;
    }

    public void setRubySapphire(RubySapphire rubySapphire) {
        this.rubySapphire = rubySapphire;
    }

}
