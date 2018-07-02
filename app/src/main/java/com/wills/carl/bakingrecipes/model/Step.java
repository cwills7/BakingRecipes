package com.wills.carl.bakingrecipes.model;


import android.support.annotation.NonNull;

import java.io.Serializable;

public class Step implements Comparable<Step>, Serializable {

    int id;
    String shortDesc;
    String description;
    String videoUrl;
    String thumbnailUrl;

    public Step (int id, String shortDesc, String description, String videoUrl, String thumbnailUrl){
        this.id = id;
        this.shortDesc = shortDesc;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }



    @Override
    public int compareTo(@NonNull Step o) {
        return 100-this.getId();
    }
}
