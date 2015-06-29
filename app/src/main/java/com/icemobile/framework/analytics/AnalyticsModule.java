package com.icemobile.framework.analytics;

import android.content.Context;

import com.icemobile.framework.analytics.tracker.AnalyticsTracker;
import com.icemobile.framework.core.module.BaseModule;
import com.icemobile.framework.core.module.provider.ModuleProvider;

/**
 * Created by Carlos on 07/05/2014.
 */
public abstract class AnalyticsModule extends BaseModule {

    public static final String MODULE_NAME = "Analytics";

    public AnalyticsModule(Context context, ModuleProvider moduleProvider) {
        super(context, moduleProvider);
    }

    @Override
    public final String getName() {
        return MODULE_NAME;
    }

    public abstract AnalyticsTracker getTrackerInstance();

}
