package com.mdgd.pokemon.models.repo.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Move {

    @SerializedName("move")
    @Expose
    private Move_ move;
    @SerializedName("version_group_details")
    @Expose
    private ArrayList<VersionGroupDetail> versionGroupDetails = new ArrayList<>();

    public Move_ getMove() {
        return move;
    }

    public void setMove(Move_ move) {
        this.move = move;
    }

    public ArrayList<VersionGroupDetail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public void setVersionGroupDetails(ArrayList<VersionGroupDetail> versionGroupDetails) {
        this.versionGroupDetails = versionGroupDetails;
    }

}
