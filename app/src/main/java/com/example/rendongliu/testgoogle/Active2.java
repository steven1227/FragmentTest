package com.example.rendongliu.testgoogle;

import android.app.Activity;
import android.os.Bundle;

import com.icemobile.framework.analytics.AnalyticsModule;
import com.icemobile.framework.analytics.tracker.AnalyticsTracker;
import com.icemobile.module.analytics.GoogleAnalyticsV2Module;


public class Active2 extends Activity {
    public static AnalyticsTracker tracker=MainActivity.tracker;
//    private AnalyticsModule analyticsModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active2);
//        analyticsModule = new GoogleAnalyticsV2Module(this, new TestModuleProvider());
        initAnalytics();

    }
    private void initAnalytics() {
//        if (analyticsModule != null) {
//            tracker = analyticsModule.getTrackerInstance();
//        }
        if (tracker!=null){
            tracker.retainSession(this);
            tracker.trackPage("Page2 Starts");
        }
    }
}
