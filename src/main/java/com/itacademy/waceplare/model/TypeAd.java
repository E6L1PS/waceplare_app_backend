package com.itacademy.waceplare.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TypeAd {
    CAR("Автомобили"),
    REAL_ESTATE("Недвижимость"),
    JOB("Работа"),
    SERVICE("Услуги"),
    STUFF("Вещи"),
    BUSINESS("Бизнес");

    private final String displayName;

    TypeAd(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("type")
    public String getDisplayName() {
        return displayName;
    }
}
