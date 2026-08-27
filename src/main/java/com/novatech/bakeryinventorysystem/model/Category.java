package com.novatech.bakeryinventorysystem.model;

public enum Category {
    DULCE("Pan dulce"),
    SALADO("Pan salado");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}