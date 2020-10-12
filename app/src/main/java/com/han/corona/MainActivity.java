package com.han.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Context mContex;
    public static String list_total_count;
    private static MainHandler mHandler = null;
    public static final int APP_NORMAL_START_MSG = 102;
    //private String[] Aread= new String[];

    //"강서구","구로구", "양천구","영등포구", "마포구", "서초구", "강남구", "송파구",  "용산구",  "종로구" ,"중구", "성동구", "동대문구", "성북구"

    public static String[] Area = {"강서구","구로구", "양천구","영등포구", "마포구", "서초구", "강남구", "송파구",  "용산구",  "종로구" ,"중구", "성동구", "동대문구", "성북구"};
    public static int[][] Area_Count = new int[3][14];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        for(int i= 0 ;i <3 ; i++){
            for(int j = 0 ; j<14 ;j++)
                Area_Count[i][j] = 0;
        }
        mContex = this;
        mHandler = new MainHandler();

        InitFragment();
//        GetCoronaData mGetCoronaData = new GetCoronaData(mContex);
//        mGetCoronaData.execute("");
        DownloadFilesTask mDownloadFilesTask = new DownloadFilesTask(true);
        mDownloadFilesTask.execute();
        //GetData();
    }


    void InitFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fr_fragment, new fragment_tab2());
        fragmentTransaction.commit();
    }


    @OnClick({R.id.bt_fragment0,R.id.bt_fragment0_0,R.id.bt_fragment1,R.id.bt_fragment1_1,R.id.bt_fragment2,R.id.bt_fragment2_2,R.id.bt_fragment3,R.id.bt_fragment3_3}) void BottomTabButton(View v){
        int id = v.getId();
        Fragment fr =  new fragment_tab0();
        if(id==R.id.bt_fragment0 || id==R.id.bt_fragment0_0){
            fr = new fragment_tab0() ;
        }else if(id==R.id.bt_fragment1|| id==R.id.bt_fragment1_1){
            fr = new fragment_tab1() ;
        }else if(id==R.id.bt_fragment2|| id==R.id.bt_fragment2_2){
            fr = new fragment_tab2() ;
        }else if(id==R.id.bt_fragment3 || id==R.id.bt_fragment3_3){
            fr = new fragment_tab3() ;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }

    void GetData(){
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/45646f626d68616e31313273566d684f/json/Corona19Status/4501/5485/"); /*URL*/
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
            String Responseline="";
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                Responseline += line;
                Log.e("HAN",line+"");
            }
            InitData(Responseline);

            rd.close();
            conn.disconnect();

            Message msg =  Message.obtain();
            msg.what = APP_NORMAL_START_MSG;
            sendDataMessage(msg);

        }catch (Exception e){
            Log.e("HAN",e+"");
        }
    }

    void GetData(String last){

//        String url = last;
//        (Integer.parseInt(last)-999)
        String url1 = "http://openapi.seoul.go.kr:8088/45646f626d68616e31313273566d684f/json/Corona19Status/"+ (Integer.parseInt(last)-999)+"/"+last;
        Log.e("HAN",url1);
        StringBuilder urlBuilder = new StringBuilder(url1); /*URL*/
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
            String Responseline="";
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                Responseline += line;
                Log.e("HAN",line+"");
            }
            //InitData(Responseline);
            ReadData(Responseline);
            rd.close();
            conn.disconnect();
            //System.out.println(sb.toString());
        }catch (Exception e){
            Log.e("HAN",e+"");
        }
    }


    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        boolean isStart = true;
        DownloadFilesTask(boolean isStart){
            this.isStart = isStart;
        }
        protected Long doInBackground(URL... urls) {
            if(isStart)
                GetData();
            else
                GetData(list_total_count);
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }

    void InitData(String allmsg){
        JSONObject jsonResult;

        try {
            jsonResult = new JSONObject(allmsg);
            jsonResult = new JSONObject(jsonResult.getString("Corona19Status"));
            //Log.e("HAN: "," jsonResult.getString(list_total_count): "+ jsonResult.getString("list_total_count")+"");
            list_total_count = jsonResult.getString("list_total_count");


        }
        catch (Exception e){
            Log.e("HAN","e: "+e);
        }
    }

    void ReadData(String allmsg){

        JSONObject jsonResult;

        try {
            jsonResult = new JSONObject(allmsg);

            Log.e("HAN: ","jsonResult: "+jsonResult+"");

            //JSONArray data = new JSONArray(jsonResult.getString("row"));

            Log.e("HAN: "," jsonResult.getString(\"Corona19Status\"): "+ jsonResult.getString("Corona19Status")+"");
            Log.e("HAN: "," jsonResult.getString(\"Corona19Status\"): "+ jsonResult.getString("Corona19Status")+"");

            jsonResult = new JSONObject(jsonResult.getString("Corona19Status"));
            Log.e("HAN: "," jsonResult.getString(rona19Status): "+ jsonResult.getString("row")+"");

            JSONArray data = new JSONArray(jsonResult.getString("row"));
            Log.e("HAN: ","data.length(): "+ data.length());
            //JSONArray list= jsonResult.getJSONArray("Corona19Status");
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonPopupObject = data.getJSONObject(i);

                //Long diff_day = differentDay(jsonPopupObject.getString("CORONA19_MDATE").substring(0,10),getDate());

                String temp = jsonPopupObject.getString("CORONA19_DATE");
                if(temp.length()==5)
                    temp = "0"+temp;

                Long diff_day = differentDay(temp,getDate());
                diff_day = diff_day - 3;
                if(diff_day<3){
                    Log.e("HAN","diff_day: "+diff_day);
                    for(int area_count = 0 ; area_count < 14 ; area_count++)
                    if(Area[area_count].equals(jsonPopupObject.getString("CORONA19_AREA"))){
                        int count = (int)(diff_day-0);
                        if(jsonPopupObject.getString("CORONA19_LEAVE_STATUS").equals(""))
                            Area_Count[count][area_count] += 1;
                    }
                }
            }

        }
        catch (Exception e){
            Log.e("HAN","e: "+e);
        }
    }

    public long differentDay(String PreviousData, String CurrentData){
        long diff_Day=0;
        try{
//            if(PreviousData.length()==5){
//                PreviousData = "0"+PreviousData;
//            }
            Log.e("HAN","PreviousData: "+PreviousData);
            Log.e("HAN","CurrentData: "+CurrentData);

            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("MM.dd.");
            Date FirstDate = format.parse(PreviousData);
            Date SecondDate = format.parse(CurrentData);

            Log.e("HAN","FirstDate: "+FirstDate);
            Log.e("HAN","SecondDate: "+SecondDate);
            long calDate = SecondDate.getTime() - FirstDate.getTime();
            diff_Day = calDate / ( 24*60*60*1000);
            return diff_Day;
        }
        catch(ParseException e)
        {
            return 0;
        }
    }

    public String getDate(){
        Date today_format = new Date();
        //SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("MM.dd.");
        String today = format1.format(today_format);
        return today;
    }

    public static void sendDataMessage(Message msg) {
        if(mHandler != null) {
            mHandler.sendMessage(msg);
        } else {

        }
    }



    public class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == APP_NORMAL_START_MSG) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DownloadFilesTask mDownloadFilesTask = new DownloadFilesTask(false);
                        mDownloadFilesTask.execute();
                    }
                }, 200); // 1sec ->  200
            }
        }
    }



    //CORONA19_DATE 발행날짜
    // CORONA19_AREA 구
    //CORONA19_LEAVE_STATUS 퇴소
}