package com.shyrkov.model;

public class Pipeline {

    private int startPointId;
    private int endPointId;
    private int length;

    public Pipeline(int startPointId, int endPointId, int length) {
        this.startPointId = startPointId;
        this.endPointId = endPointId;
        this.length = length;
    }

    public static Pipeline parseFromStringArray(String[] params){
        int startPointId = Integer.parseInt(params[0]);
        int endPointId = Integer.parseInt(params[1]);
        int length = Integer.parseInt(params[2]);

        return new Pipeline(startPointId, endPointId, length);
    }

    public int getStartPointId() {
        return startPointId;
    }

    public int getEndPointId() {
        return endPointId;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Pipeline{" +
                "startPointId=" + startPointId +
                ", endPointId=" + endPointId +
                ", length=" + length +
                '}';
    }
}
