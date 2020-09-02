package com.mdgd.pokemon.models.repo.schemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Move {

    @Expose
    @SerializedName("move")
    private Move_ move;

    @Expose
    @SerializedName("version_group_details")
    private List<VersionGroupDetail> versionGroupDetails = new ArrayList<>();

    public Move_ getMove() {
        return move;
    }

    public void setMove(Move_ move) {
        this.move = move;
    }

    public List<VersionGroupDetail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public void setVersionGroupDetails(List<VersionGroupDetail> versionGroupDetails) {
        this.versionGroupDetails = versionGroupDetails;
    }
}
