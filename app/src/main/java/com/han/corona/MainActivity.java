package com.han.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Context mContex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        InitFragment();
    }


    void InitFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fr_fragment, new fragment_tab0());
        fragmentTransaction.commit();
    }

    @OnClick({R.id.bt_fragment0,R.id.bt_fragment1,R.id.bt_fragment2}) void BottomTabButton(View v){
        int id = v.getId();
        Fragment fr =  new fragment_tab0();
        if(id==R.id.bt_fragment0){
            fr = new fragment_tab0() ;
        }else if(id==R.id.bt_fragment1){
            fr = new fragment_tab1() ;
        }else if(id==R.id.bt_fragment2){
            fr = new fragment_tab2() ;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }
}