package com.library.rendong.log;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.rendongliu.testgoogle.R;
import com.icemobile.framework.analytics.tracker.AnalyticsTracker;
import com.icemobile.module.analytics.GoogleAnalyticsV2Module;



/**
 * Created by rendong.liu on 29/06/15.
 */
public class DebugTracker implements AnalyticsTracker {


    private static boolean IS_START=false;
    private Handler hand;
    private int logcount=0;
    private int sessionCount = 0;
    private SimpleDateFormat formatter;
    private TrackLogger logger;
    private Context context;

    private GoogleAnalyticsV2Module caller;
    private final static String TAG = "GoogleAnalyticsTracker";

    public DebugTracker(){

    }

    public DebugTracker(GoogleAnalyticsV2Module temp, Context context){

        logger=new TrackLogger();
        formatter= new SimpleDateFormat("dd-MM HH:mm:ss");
        this.caller=temp;
        this.context=context;
        this.hand=new Handler();

    }

    @Override
    public void retainSession(Context ctx) {
        if (caller.isTrackable()) {
            if (sessionCount == 0) {
                final String trackingId = caller.getTrackingId();
                Log.d(TAG, "startSession: TrackingID=" + trackingId + " Context=" + ctx);
            }
            sessionCount++;
            Log.d(TAG, "sessionCount: " + sessionCount);

        }
    }

    @Override
    public void releaseSession() {

        if (caller.isTrackable()) {
            if (sessionCount > 0) {
                sessionCount--;
            }
        }
    }

    @Override
    public void trackEvent(String category, String action, String label, long value) {
        if (caller.isTrackable()) {
            Log.d(TAG, "trackEvent: category=" + category + " action=" + action + " label=" + label + " value=" + value);
            logger.add(new TrackEvent(category, action, String.valueOf(label), value, formatter.format(new Date())));

            Log.e(TAG, logger.parsetToJson());

            logcount++;
            if(logcount==(context.getResources().getInteger(R.integer.max_log_number))){
                store();
                logcount=0;
            }
        }
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private synchronized void store() {
        if (isExternalStorageWritable()) {

            String newLog=logger.parsetToJson();
            if(newLog!=null){
                try {
                    File LogFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), context.getString(R.string.log_file_name));

                    if (!LogFile.exists()) {
                        LogFile.createNewFile();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(LogFile, true));
                        writer.write(logger.parsetToJson());
                        writer.close();
                        Log.d(TAG, "store it once");
                    }else{
                        FileInputStream fis = new FileInputStream(LogFile);
                        byte[] data = new byte[(int) LogFile.length()];
                        fis.read(data);
                        fis.close();
                        String str = new String(data, "UTF-8");
                        Log.e(TAG,str+"????");
                        str=str.substring(0,str.length()-1);
                        BufferedWriter writer = new BufferedWriter(new FileWriter(LogFile, false));
                        writer.write(str + "," + newLog.substring(1, newLog.length()));
                        writer.close();
                        Log.d(TAG, "store it once");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    logger.clean();
            }
        }
    }

    @Override
    public void trackPage(String name) {
        if (caller.isTrackable()) {
            if (!IS_START) {
                IS_START = true;
                storeLogbytime();
                Log.e("Google", "Google");
            }
                Log.d(TAG, "trackPage: " + name);
                logger.add(new TrackPage(name, formatter.format(new Date())));

                Log.e(TAG, logger.parsetToJson());
                logcount++;
                if (logcount == (context.getResources().getInteger(R.integer.max_log_number))) {
                    store();
                    logcount = 0;
                }

        }
    }
    private void storeLogbytime() {
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                store();
                Log.e(TAG, "?" + logger.parsetToJson());
                storeLogbytime();

            }
        },context.getResources().getInteger(R.integer.sava_interval));
    }

    @Override
    public void trackPage(int resIdString) {
        final String name = context.getString(resIdString);
        trackPage(name);
    }

    @Override
    public void setDimension(int dimension, String value) {
        if (caller.isTrackable()) {
            Log.d(TAG, "set custom dimension:" + dimension + " value:" + value);
        }
    }

}


