package com.example.steven.testanalytics;

import android.app.Fragment;
import android.os.*;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by steven on 28-6-15.
 */
public class Headings extends Fragment {
    private String[] hs={"title1","title2","title3"};
    private ListView headings;
    private OnHeadlineSelectedListener face;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        face=(OnHeadlineSelectedListener)(getActivity().getFragmentManager().findFragmentById(R.id.article_fragment));
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp=inflater.inflate(R.layout.heading,container,false);
        this.headings=(ListView)temp.findViewById(R.id.headings);
        headings.setAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                hs
        ));




        headings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                face.onArticleSelected(hs[position]);
                if(position==2){
                    Message a=new Message();
                    getActivity().getFragmentManager().beginTransaction().add(R.id.fragment_container,a,"tag").commit();
                    Log.d(" is that working"," "+a+" ");
                }else {
                    Fragment temp=getFragmentManager().findFragmentByTag("tag");
                    if(temp!=null)
                        getActivity().getFragmentManager().beginTransaction().remove(temp).commit();
                }
            }
        });
        return temp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
