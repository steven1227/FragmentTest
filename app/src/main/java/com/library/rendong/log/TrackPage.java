package com.library.rendong.log;
import com.google.gson.Gson;
import android.util.Log;

import java.util.Date;

/**
 * Created by rendong.liu on 30/06/15.
 */
public class TrackPage extends TrackInfo {
    private String pagename;

    public TrackPage(String pagename,String time){
        this.pagename=pagename;
        setTime(time);
    }

    @Override
    public void parseJson() {

    }
}
