package com.example.rendongliu.testgoogle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icemobile.framework.analytics.tracker.AnalyticsTracker;

/**
 * Created by rendong.liu on 26/06/15.
 */
public class ArticalFragment extends Fragment implements changetext {
    TextView passage;
    private AnalyticsTracker tracker=Active2.tracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp=inflater.inflate(R.layout.article_view,container,false);

        passage=(TextView)temp.findViewById(R.id.testpage);
        return temp;
    }

    @Override
    public void onArticleSelected(int position) {
        switch (position){

            case 0:{
                passage.setText("stamp1");
                tracker.trackPage("stamp1");
                break;
            }

            case 1:{
                passage.setText("stamp2");
                tracker.trackPage("stamp2");
                break;
            }

            case 2:{
                passage.setText("stamp3");
                tracker.trackPage("stamp3");
                break;
            }
        }
    }
}

interface changetext{
    public void onArticleSelected(int position);
}
