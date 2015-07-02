package com.example.rendongliu.testgoogle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.icemobile.framework.analytics.AnalyticsModule;
import com.icemobile.framework.analytics.tracker.AnalyticsTracker;
import com.icemobile.framework.core.module.provider.ModuleProvider;
import com.icemobile.module.analytics.GoogleAnalyticsV2Module;


public class MainActivity extends FragmentActivity {

    public static AnalyticsTracker tracker;
    private AnalyticsModule analyticsModule;
    private TextView text;
    private String[] mPlanetTitles={"test1","test2","test3"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        com.library.rendong.log.Log.clear();
        analyticsModule = new GoogleAnalyticsV2Module(this.getApplicationContext(), new TestModuleProvider());
        initAnalytics();
        setContentView(R.layout.activity_main);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mPlanetTitles));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tracker.trackEvent("click menu item", "click item" + position, null, 0);
            }
        });

       mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                tracker.trackEvent("open menu","open menu",null,0);
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

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

        View view=getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tracker.trackEvent("Quit", "I am leaving", null, 0);
//        com.library.rendong.log.Log.store();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
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
