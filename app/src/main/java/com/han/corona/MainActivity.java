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

    //private String[] Aread= new String[];
    String[] Area = { "강남구","강동구","강북구","강서구","관악구","광진구","구로구",
            "금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구",
            "성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"};
    public static int[][] Area_Count = new int[3][25];
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
            ReadData(Responseline);
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

                Long diff_day = differentDay(jsonPopupObject.getString("CORONA19_DATE"),getDate());
                if(diff_day<3){
                    Log.e("HAN","3일 이내");
                    for(int area_count = 0 ; area_count < 25 ; area_count++)
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
            Log.e("HAN","PreviousData: "+PreviousData);
            Log.e("HAN","CurrentData: "+CurrentData);

            SimpleDateFormat format = new SimpleDateFormat("MM.dd.");
            Date FirstDate = format.parse(PreviousData);
            Date SecondDate = format.parse(CurrentData);

//            Log.e("HAN","FirstDate: "+FirstDate);
//            Log.e("HAN","SecondDate: "+SecondDate);
            long calDate = SecondDate.getTime() - FirstDate.getTime();
            Log.e("HAN","FirstDate: "+FirstDate);
            Log.e("HAN","SecondDate: "+SecondDate);
            diff_Day = calDate / ( 24*60*60*1000);
            Log.e("HAN","diff_Day: "+diff_Day);
            return diff_Day;
        }
        catch(ParseException e)
        {
            return 0;
        }
    }

    public String getDate(){
        Date today_format = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat ( "MM.dd.");
        String today = format1.format(today_format);
        return today;
    }



    //CORONA19_DATE 발행날짜
    // CORONA19_AREA 구
    //CORONA19_LEAVE_STATUS 퇴소
}