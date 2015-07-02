package com.library.rendong.log;
import com.google.gson.Gson;
import android.util.Log;

import java.util.Date;

/**
 * Created by rendong.liu on 30/06/15.
 */
public class TrackEvent extends TrackInfo{

    private String category;
    private String action;
    private String label;
    private long value;

    public TrackEvent (String category,String action, String label, long value,String time){
        this.category=category;
        this.action=action;
        this.label=label;
        this.value=value;
        setTime(time);
    }

    @Override
    public void parseJson() {

    }
}
