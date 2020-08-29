package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerationVi {

    @Expose
    @Embedded
    @SerializedName("omegaruby-alphasapphire")
    private OmegarubyAlphasapphire omegarubyAlphasapphire;

    @Embedded
    @Expose
    @SerializedName("x-y")
    private XY xY;

    public OmegarubyAlphasapphire getOmegarubyAlphasapphire() {
        return omegarubyAlphasapphire;
    }

    public void setOmegarubyAlphasapphire(OmegarubyAlphasapphire omegarubyAlphasapphire) {
        this.omegarubyAlphasapphire = omegarubyAlphasapphire;
    }

    public XY getXY() {
        return xY;
    }

    public void setXY(XY xY) {
        this.xY = xY;
    }

}
