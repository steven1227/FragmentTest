package com.icemobile.module.analytics;

import android.content.Context;
import android.util.Log;

import com.icemobile.framework.analytics.AnalyticsModule;
import com.icemobile.framework.analytics.tracker.AnalyticsTracker;
import com.icemobile.framework.core.module.provider.ModuleProvider;

public class GoogleAnalyticsV2Module extends AnalyticsModule {

	private final static String TAG = "GoogleAnalyticsTracker";
	private final static int INTERVAL_SEC = 120;

	private AnalyticsTracker atracker = null;

//    private final String trackingId;

	public GoogleAnalyticsV2Module(final Context context, final ModuleProvider moduleProvider) {
		super(context, moduleProvider);

//        if (CafBuild.ENV == BuildEnvironment.PRODUCTION) {
//            trackingId = ResUtil.getString(context, R.string.analytics_tracking_id_production);
//        }
//        else if (CafBuild.ENV == BuildEnvironment.ACCEPTANCE) {
//            trackingId = ResUtil.getString(context, R.string.analytics_tracking_id_acceptance);
//        }
//        else {
//            trackingId = ResUtil.getString(context, R.string.analytics_tracking_id_debug);
//        }

    }

    protected String getTrackingId(){
        return "trackingId";
    }

	private boolean isTrackable(){
		if("".equals(getTrackingId()) || !isEnabled()) {
			return false;
		}

		return true;
	}

	/**
	 * Get a singleton instance of the Google Analytics tracker
	 * 
	 * @return AnalyticsTracker tracker
	 */
	@Override
	public synchronized AnalyticsTracker getTrackerInstance() {
		if(atracker == null){
			atracker = new IceGoogleAnalyticsTracker();
		}

		return atracker;
	}

	private class IceGoogleAnalyticsTracker implements AnalyticsTracker{
		private int sessionCount = 0;
//		private final GoogleAnalytics ga;

		public IceGoogleAnalyticsTracker() {
//			GAServiceManager.getInstance().setDispatchPeriod(INTERVAL_SEC);
//			ga = GoogleAnalytics.getInstance(getContext());

//			if(getContext().getResources().getBoolean(R.bool.analytics_track_uncaught_exceptions)){
//				Log.d("GoogleAnalyticsTracker", "Added exception handler!");
//				final UncaughtExceptionHandler myHandler = new ExceptionReporter(
//						ga.getTracker(getTrackingId()), GAServiceManager.getInstance(),
//						Thread.getDefaultUncaughtExceptionHandler());
//
//				Thread.setDefaultUncaughtExceptionHandler(myHandler);
//			}


//			if(CafBuild.DEBUG){
//				ga.setDebug(true);
//			}
		}

		/**
		 * Track a screen view identified by name
		 * @param name
		 */
		@Override
		public void trackPage(final String name) {
			if(isTrackable()){
				Log.d(TAG, "trackPage: " + name);
//				ga.getTracker(getTrackingId()).sendView(name);
			}
		}

		/**
		 * Track an event identified by a category, action, label and value.
		 * @param category
		 * @param action
		 * @param label
		 * @param value
		 */
		@Override
		public void trackEvent(final String category, final String action, final String label,
				final long value) {
			if(isTrackable()){
				Log.d(TAG, "trackEvent: category=" + category + " action=" + action + " label=" + label + " value=" + value);
//				ga.getTracker(getTrackingId()).sendEvent(category, action, label, value);
			}
		}

		/**
		 * Decrease retain count by 1. If retain count is 0, the session will be stopped and a
		 * final dispatch is executed.
		 */
		@Override
		public synchronized void releaseSession() {
			if(isTrackable()){
				if(sessionCount > 0) {
					sessionCount--;
				}

//				Log.d(TAG, "stopSession - sessions left: " + sessionCount);
//
//				if(sessionCount == 0){
//					Log.d(TAG, "stopping Session");
//					GAServiceManager.getInstance().dispatch();
//				}
			}
		}

		/**
		 * Retains session. If no session available, a new one will be created.
		 * @param ctx
		 */
		@Override
		public synchronized void retainSession(final Context ctx) {
			if(isTrackable()){
				if(sessionCount == 0){
					final String trackingId = getTrackingId();
					Log.d(TAG, "startSession: TrackingID=" + trackingId + " Context=" + ctx);
//					ga.getTracker(getTrackingId()).setStartSession(true);
				}

				sessionCount++;
				Log.d(TAG, "sessionCount: " + sessionCount);
			}
		}

		/**
		 * Track a screen view identified by a resource id.
		 * @param resIdString resource id
		 */
		@Override
		public void trackPage(final int resIdString) {
			final String name = getContext().getString(resIdString);
			trackPage(name);
		}

		@Override
		public void setDimension(final int dimension, final String value) {
			if (isTrackable()) {
				Log.d(TAG, "set custom dimension:" + dimension + " value:" + value);
				//dispatch before changing custom dimension to tracker
//				GAServiceManager.getInstance().dispatch();
//				ga.getTracker(getTrackingId()).setCustomDimension(dimension, value);
			}
		}
	}
}
