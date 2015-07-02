package com.library.rendong.log;

/**
 * Created by rendong.liu on 30/06/15.
 */
public abstract class TrackInfo {

    private String time;

    public String getTime() {
        return time;
    }

    abstract void parseJson();

    public void setTime(String time) {
        this.time = time;
    }
}
