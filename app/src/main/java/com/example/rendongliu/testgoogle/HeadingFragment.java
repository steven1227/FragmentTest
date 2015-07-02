package com.example.rendongliu.testgoogle;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.icemobile.framework.analytics.tracker.AnalyticsTracker;

import java.util.ArrayList;

/**
 * Created by rendong.liu on 26/06/15.
 */
public class HeadingFragment extends Fragment {
    private ListView heading;
    private String[] items={"stamp1","stamp2","stamp3"};
    private changetext listener;
    private AnalyticsTracker tracker;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tracker=Active2.tracker;
        listener=(changetext)getFragmentManager().findFragmentById(R.id.article_fragment3);
        Log.d(getClass().getName(), "hello1---\n" + getActivity().getFragmentManager().findFragmentById(R.id.article_fragment3));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp=inflater.inflate(R.layout.heading_view,container,false);

        listener=(changetext)getFragmentManager().findFragmentById(R.id.article_fragment3);
        Log.d(getClass().getName(),"hello2---\n"+getActivity().getFragmentManager().findFragmentById(R.id.article_fragment3));
        heading=(ListView)temp.findViewById(R.id.headinglist);
        heading.setAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                items
        ));
        heading.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onArticleSelected(position);
                ((TextView)view).setTextColor(Color.RED);

                int i =parent.getChildCount();
                for (int j=0;j<i;j++) {
                    if(j!=position){
                        ((TextView)parent.getChildAt(j)).setTextColor(Color.WHITE);
                    }
                }

                Log.e("Google", (String) heading.getItemAtPosition(position));

                tracker.trackEvent("View different stamps", "see stamp" + position, null, 0);
            }
        });
        return temp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
