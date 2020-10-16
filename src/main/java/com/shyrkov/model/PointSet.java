package com.shyrkov.model;

public class PointSet {
    private Integer pointA;
    private Integer pointB;
//    private Boolean hasRoute;
//    private Integer length;

//    public PointSet(Integer pointA, Integer pointB, Boolean hasRoute, Integer length) {
//        this.pointA = pointA;
//        this.pointB = pointB;
//        this.hasRoute = hasRoute;
//        this.length = length;
//    }

    public PointSet(Integer pointA, Integer pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

//    public Boolean isHasRoute() {
//        return hasRoute;
//    }
//
//    public Integer getLength() {
//        return length;
//    }
//
    public Integer getPointA() {
        return pointA;
    }

    public Integer getPointB() {
        return pointB;
    }
}
