package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sprites {

    @Expose
    @SerializedName("back_default")
    private String backDefault;

    @Expose
    @SerializedName("back_female")
    private String backFemale;

    @Expose
    @SerializedName("back_shiny")
    private String backShiny;

    @Expose
    @SerializedName("back_shiny_female")
    private String backShinyFemale;

    @ColumnInfo(name = "SpritesFrontDefault")
    @Expose
    @SerializedName("front_default")
    private String frontDefault;

    @Expose
    @SerializedName("front_female")
    private String frontFemale;

    @Expose
    @SerializedName("front_shiny")
    private String frontShiny;

    @Expose
    @SerializedName("front_shiny_female")
    private String frontShinyFemale;

    @Embedded
    @Expose
    @SerializedName("other")
    private Other other;

//    @Embedded
//    @Expose
//    @SerializedName("versions")
//    private Versions versions;

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackFemale() {
        return backFemale;
    }

    public void setBackFemale(String backFemale) {
        this.backFemale = backFemale;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    public String getBackShinyFemale() {
        return backShinyFemale;
    }

    public void setBackShinyFemale(String backShinyFemale) {
        this.backShinyFemale = backShinyFemale;
    }

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

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public String getFrontShinyFemale() {
        return frontShinyFemale;
    }

    public void setFrontShinyFemale(String frontShinyFemale) {
        this.frontShinyFemale = frontShinyFemale;
    }

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

//    public Versions getVersions() {
//        return versions;
//    }

//    public void setVersions(Versions versions) {
//        this.versions = versions;
//    }

}
