package com.icemobile.framework.analytics.tracker;

import android.content.Context;

/**
* Created by nick on 2/28/14.
*/
public interface AnalyticsTracker {

    /**
     * Retains session. If no session available, a new one will be created.
     * @param ctx
     */
    public abstract void retainSession(Context ctx);

    /**
     * Decrease retain count by 1. If retain count is 0, the session will be stopped.
     */
    public abstract void releaseSession();

    public abstract void trackEvent(String category, String action, String label, long value);
    public abstract void trackPage(String name);
    public abstract void trackPage(int resIdString);

    public abstract void setDimension(int dimension, String value);
}
