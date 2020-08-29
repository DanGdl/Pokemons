package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Versions {

    @Embedded
    @Expose
    @SerializedName("generation-i")
    private GenerationI generationI;

    @Embedded
    @Expose
    @SerializedName("generation-ii")
    private GenerationIi generationIi;

    @Embedded
    @Expose
    @SerializedName("generation-iii")
    private GenerationIii generationIii;

    @Embedded
    @Expose
    @SerializedName("generation-iv")
    private GenerationIv generationIv;

    @Embedded
    @Expose
    @SerializedName("generation-v")
    private GenerationV generationV;

    @Embedded
    @Expose
    @SerializedName("generation-vi")
    private GenerationVi generationVi;

    @Embedded
    @Expose
    @SerializedName("generation-vii")
    private GenerationVii generationVii;

    @Embedded
    @Expose
    @SerializedName("generation-viii")
    private GenerationViii generationViii;

    public GenerationI getGenerationI() {
        return generationI;
    }

    public void setGenerationI(GenerationI generationI) {
        this.generationI = generationI;
    }

    public GenerationIi getGenerationIi() {
        return generationIi;
    }

    public void setGenerationIi(GenerationIi generationIi) {
        this.generationIi = generationIi;
    }

    public GenerationIii getGenerationIii() {
        return generationIii;
    }

    public void setGenerationIii(GenerationIii generationIii) {
        this.generationIii = generationIii;
    }

    public GenerationIv getGenerationIv() {
        return generationIv;
    }

    public void setGenerationIv(GenerationIv generationIv) {
        this.generationIv = generationIv;
    }

    public GenerationV getGenerationV() {
        return generationV;
    }

    public void setGenerationV(GenerationV generationV) {
        this.generationV = generationV;
    }

    public GenerationVi getGenerationVi() {
        return generationVi;
    }

    public void setGenerationVi(GenerationVi generationVi) {
        this.generationVi = generationVi;
    }

    public GenerationVii getGenerationVii() {
        return generationVii;
    }

    public void setGenerationVii(GenerationVii generationVii) {
        this.generationVii = generationVii;
    }

    public GenerationViii getGenerationViii() {
        return generationViii;
    }

    public void setGenerationViii(GenerationViii generationViii) {
        this.generationViii = generationViii;
    }

}
