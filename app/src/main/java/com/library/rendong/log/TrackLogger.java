package com.library.rendong.log;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rendong.liu on 30/06/15.
 */
public class TrackLogger {
    private List<TrackInfo> trackInfoList;

    public TrackLogger(){
        this.trackInfoList =new ArrayList<>();
    }
    public boolean add(TrackInfo object) {
        return trackInfoList.add(object);
    }

    public String parsetToJson(){
        if(trackInfoList.isEmpty())
        {
            return null;
        }else {
            Gson gson = new Gson();
            return gson.toJson(this.trackInfoList);
        }
    }

    public void clean(){
        this.trackInfoList.clear();
    }
}
