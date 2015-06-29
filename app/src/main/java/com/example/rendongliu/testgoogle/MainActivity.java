package com.example.rendongliu.testgoogle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;
import com.icemobile.framework.analytics.AnalyticsModule;
import com.icemobile.framework.analytics.tracker.AnalyticsTracker;
import com.icemobile.framework.core.module.provider.ModuleProvider;
import com.icemobile.module.analytics.GoogleAnalyticsV2Module;


public class MainActivity extends FragmentActivity {

    public static AnalyticsTracker tracker;
    private AnalyticsModule analyticsModule;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        analyticsModule = new GoogleAnalyticsV2Module(this, new TestModuleProvider());
        initAnalytics();
        setContentView(R.layout.activity_main);

        text=(TextView)findViewById(R.id.text);
        Button click1=(Button)findViewById(R.id.click1);
        click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), Active2.class);
                startActivity(a);
                tracker.trackEvent("Activity jump", "click go to 2", null, 0);
            }
        });

        Button click2=(Button)findViewById(R.id.click2);
        click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View temp=findViewById(R.id.viewPager);
                if(temp!=null){
                    tracker.trackEvent("remove fragment", "slides miss", null, 0);
                    ((ViewManager)findViewById(R.id.viewPager).getParent()).removeView(temp);
                }
            }
        });

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        pager.setPageTransformer(true, new ZoomOutPageTransformer());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tracker.trackPage("slide" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initAnalytics() {

        if (analyticsModule != null) {
            tracker = analyticsModule.getTrackerInstance();
        }
        if (tracker!=null){
            Log.d("----yes----","tracker exists");
            tracker.retainSession(this);
            tracker.trackPage("App Starts");
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tracker.trackPage("Main page Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        tracker.trackPage("Main page Pause");

    }

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.95f;
        private static final float MIN_ALPHA = 0.6f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }

    }

}
