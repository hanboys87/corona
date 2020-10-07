package com.han.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Context mContex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContex = this;
        //InitFragment();
//        GetCoronaData mGetCoronaData = new GetCoronaData(mContex);
//        mGetCoronaData.execute("");
        DownloadFilesTask mDownloadFilesTask = new DownloadFilesTask();
        mDownloadFilesTask.execute();
        //GetData();
    }


    void InitFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fr_fragment, new fragment_tab0());
        fragmentTransaction.commit();
    }

    @OnClick({R.id.bt_fragment0,R.id.bt_fragment1,R.id.bt_fragment2,R.id.bt_fragment3}) void BottomTabButton(View v){
        int id = v.getId();
        Fragment fr =  new fragment_tab0();
        if(id==R.id.bt_fragment0){
            fr = new fragment_tab0() ;
        }else if(id==R.id.bt_fragment1){
            fr = new fragment_tab1() ;
        }else if(id==R.id.bt_fragment2){
            fr = new fragment_tab2() ;
        }else if(id==R.id.bt_fragment3){
            fr = new fragment_tab3() ;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }

    void GetData(){
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/45646f626d68616e31313273566d684f/json/Corona19Status/4501/5400/"); /*URL*/


        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                Log.e("HAN",line+"");
            }
            rd.close();
            conn.disconnect();
            //System.out.println(sb.toString());
        }catch (Exception e){
            Log.e("HAN",e+"");
        }


    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            GetData();
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }

}