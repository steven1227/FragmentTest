package com.example.rendongliu.testgoogle;

import java.security.PublicKey;
import java.util.ArrayList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rendong.liu on 29/06/15.
 */
public class Log {

    public static final boolean IS_ENABLED = true;

//    private static JSONArray EventArray;
    private static class?? PageArray ;

    /**
     * Send a { } log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(final String tag, final String msg) {
        /*
             where to store the json file
         */
        if(msg.indexOf("trackPage")>=0) {

            JSONObject temp=new JSONObject();
            try {
                temp.put("trackPage",msg.substring(msg.indexOf(":")+2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PageArray.put(temp);
//            android.util.Log.e(tag, msg + "---what I am looking for---:" +PageArray.toString());
        }else if(msg.indexOf("trackEvent")>=0){
            JSONObject temp=new JSONObject();

            try {
                temp.put("trackEvent",msg.substring(msg.indexOf(":")+2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PageArray.put(temp);
        }

        android.util.Log.e(tag, msg + "---what I am looking for---:" +PageArray.toString());
        return (isLoggingEnabled() ? android.util.Log.d(tag, msg) : 0);
    }


    private static boolean isLoggingEnabled() {
        return IS_ENABLED;
    }

    public static int store(){
        JSONObject pagelog=new JSONObject();
        try {
            pagelog.put("trackLog",PageArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        android.util.Log.e("GoogleAnalyticsTracker","---what I am looking for---:"+pagelog);
        return 0;
    }

    public static void clear(){
//        EventArray = new JSONArray();
        PageArray=new JSONArray();
        JSONObject temp=new JSONObject();
        try {
            temp.put("LogTime",(new java.util.Date()).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PageArray.put(temp);

    }

    class EventFormat{
        private String category;
        private String action;
        private String label;
        private String value;
        public EventFormat(String category,String action, String label, String value){
            this.category=category;
            this.action=action;
            this.label=label;
            this.value=value;
        }
    }
}


