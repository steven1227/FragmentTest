package com.example.steven.testanalytics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by steven on 28-6-15.
 */
public class Passeges extends Fragment implements OnHeadlineSelectedListener {
    private TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp=inflater.inflate(R.layout.passege,container,false);
        text=(TextView)temp.findViewById(R.id.text);
        return temp;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onArticleSelected(String s) {
        text.setText(s);
    }
}

interface OnHeadlineSelectedListener {
    public void onArticleSelected(String s);
}