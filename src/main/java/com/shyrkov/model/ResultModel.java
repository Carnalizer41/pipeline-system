package com.shyrkov.model;

public class ResultModel {
    private Boolean hasRoute;
    private String length;

    public ResultModel(Boolean hasRoute, String length) {
        this.hasRoute = hasRoute;
        this.length = length;
    }

    public Boolean getHasRoute() {
        return hasRoute;
    }

    public String getLength() {
        return length;
    }
}
