package com.example.steven.testanalytics;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends FragmentActivity {
    private Button goto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goto2=(Button)findViewById(R.id.goto2);
        goto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(getApplicationContext(),Activity2.class);
                startActivity(a);
            }
        });

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
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
            return 5;
        }
    }
}
