package com.mdgd.pokemon.models.repo.schemas;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mdgd.pokemon.models.repo.dao.schemas.MoveSchema;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "VersionGroupDetails", indices = {@Index("id")})
public class VersionGroupDetail {

    @ForeignKey(entity = MoveSchema.class, parentColumns = "id", childColumns = "moveId", onDelete = CASCADE)
    public long moveId;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @Expose
    @SerializedName("level_learned_at")
    private Integer levelLearnedAt;

    @Embedded
    @Expose
    @SerializedName("move_learn_method")
    private MoveLearnMethod moveLearnMethod;

    @Embedded
    @Expose
    @SerializedName("version_group")
    private VersionGroup versionGroup;

    public long getMoveId() {
        return moveId;
    }

    public void setMoveId(long moveId) {
        this.moveId = moveId;
    }

    public Integer getLevelLearnedAt() {
        return levelLearnedAt;
    }

    public void setLevelLearnedAt(Integer levelLearnedAt) {
        this.levelLearnedAt = levelLearnedAt;
    }

    public MoveLearnMethod getMoveLearnMethod() {
        return moveLearnMethod;
    }

    public void setMoveLearnMethod(MoveLearnMethod moveLearnMethod) {
        this.moveLearnMethod = moveLearnMethod;
    }

    public VersionGroup getVersionGroup() {
        return versionGroup;
    }

    public void setVersionGroup(VersionGroup versionGroup) {
        this.versionGroup = versionGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
