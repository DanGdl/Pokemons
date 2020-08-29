package com.mdgd.pokemon.models.repo.dao.schemas;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.schemas.VersionGroupDetail;

import java.util.ArrayList;
import java.util.List;

public class MoveFullSchema {

    @Embedded
    private MoveSchema move;

    @Expose
    @SerializedName("version_group_details")
    private List<VersionGroupDetail> versionGroupDetails = new ArrayList<>();

    public MoveSchema getMove() {
        return move;
    }

    public void setMove(MoveSchema move) {
        this.move = move;
    }

    public List<VersionGroupDetail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public void setVersionGroupDetails(List<VersionGroupDetail> versionGroupDetails) {
        this.versionGroupDetails = versionGroupDetails;
    }
}
