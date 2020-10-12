package com.han.corona;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;


public class fragment_tab0 extends Fragment {


    BarChart chart2;

    public fragment_tab0() {
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tab0, container, false);
        ButterKnife.bind(this, view);
        chart2  = (BarChart)view.findViewById(R.id.tab1_chart_2);
        setBarChart();

        return view;
    }


//    String[] Area = {"강서구","구로구", "양천구","영등포구", "마포구", "서초구", "강남구", "송파구",  "용산구",  "종로구" ,"중구", "성동구", "동대문구", "성북구"};
//    public static int[][] Area_Count = new int[3][14];


    private void setBarChart() {
        chart2.clearChart();
        int index = 2;

        chart2.addBar(new BarModel("강서구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][0])), 0xFF56B7F1));
        chart2.addBar(new BarModel("구로구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][1])), 0xFF56B7F1));
        chart2.addBar(new BarModel("양천구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][2])), 0xFF56B7F1));
        chart2.addBar(new BarModel("영등포구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][3])), 0xFF56B7F1));
        chart2.addBar(new BarModel("마포구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][4])), 0xFF56B7F1));

        chart2.addBar(new BarModel("서초구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][5])), 0xFF56B7F1));
        chart2.addBar(new BarModel("강남구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][6])), 0xFF56B7F1));
        chart2.addBar(new BarModel("송파구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][7])), 0xFF56B7F1));

        chart2.addBar(new BarModel("용산구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][8])), 0xFF56B7F1));
        chart2.addBar(new BarModel("종로구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][9])), 0xFF56B7F1));
        chart2.addBar(new BarModel("중구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][10])), 0xFF56B7F1));
        chart2.addBar(new BarModel("성동구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][11])), 0xFF56B7F1));
        chart2.addBar(new BarModel("동대문구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][12])), 0xFF56B7F1));
        chart2.addBar(new BarModel("성북구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][13])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("서초구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][14])), 0xFF56B7F1));
//
//        chart2.addBar(new BarModel("성동구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][15])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("성북구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][16])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("송파구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][17])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("양천구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][18])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("영등포구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][19])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("용산구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][20])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("은평구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][21])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("종로구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][22])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("중구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][23])), 0xFF56B7F1));
//        chart2.addBar(new BarModel("중랑구", Float.parseFloat(Integer.toString(MainActivity.Area_Count[index][24])), 0xFF56B7F1));

        chart2.startAnimation();
    }

}