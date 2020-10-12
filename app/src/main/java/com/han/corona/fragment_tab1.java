package com.han.corona;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;


public class fragment_tab1 extends Fragment {



    @BindView(R.id.tv_gangbook)
    TextView tv_gangbook;
    @BindView(R.id.tv_gangnam)
    TextView tv_gangnam;
    @BindView(R.id.tv_gangseo)
    TextView tv_gangseo;
    public fragment_tab1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        Recommend();
        return view;
    }

    void Recommend(){

        int temp = 0;
        int index = 0;
        int count = 0;
        for(int i = 0; i< 5 ; i++){
            if(temp > MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i]) {
                index = i;
                count = MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i];
            }
            temp = MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i];
        }
        tv_gangseo.setText(MainActivity.Area[index]+"("+count+"명"+")");

        count = 0;
        temp = 0;
        index = 5;
        for(int i = 5; i< 8 ; i++){
            if(temp > MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i]) {
                index = i;
                count = MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i];
            }
            temp = MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i];
        }
        tv_gangnam.setText(MainActivity.Area[index]+"("+count+"명"+")");

        count = 0;
        temp = 0;
        index = 8;
        for(int i = 8; i< 14 ; i++){
            if(temp > MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i]) {
                index = i;
                count = MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i];
            }
            temp = MainActivity.Area_Count[0][i] + MainActivity.Area_Count[1][i] + MainActivity.Area_Count[2][i];
        }
        tv_gangbook.setText(MainActivity.Area[index]+"("+count+"명"+")");

    }

}