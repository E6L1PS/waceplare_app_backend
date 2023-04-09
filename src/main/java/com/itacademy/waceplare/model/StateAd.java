package com.itacademy.waceplare.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StateAd {
    NEW("новое"),
    USED("б/у");

    private final String displayName;

    StateAd(String displayName) {
        this.displayName = displayName;
    }

   /* @JsonProperty("state")
    public String getDisplayName() {
        return displayName;
    }*/
}
