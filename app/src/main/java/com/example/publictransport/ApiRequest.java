package com.example.publictransport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class ApiRequest extends AsyncTask {
    public static String TAG = ApiRequest.class.getSimpleName();

    @Override
    protected ArrayList<StationInfoInstance> doInBackground(Object[] objects) {
        String requestResponse = null;

        try {
            URL url = new URL("https://data.smartdublin.ie/dublinbikes-api/last_snapshot/");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null) {
                    responseStrBuilder.append(inputStr);
                }

                requestResponse = responseStrBuilder.toString();


            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.d(TAG, "queryApi: " + e);
        }
        
        return parseJson(requestResponse);
    }
    public static ArrayList<StationInfoInstance> parseJson(String stringObject) {
        String string=stringObject.toString();
        ArrayList<StationInfoInstance> stationInfo= new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
        string = string.substring(1, string.length() - 1);
        Context context = MyApplication.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.publictransport", Context.MODE_PRIVATE);
        try {
            while(!string.isEmpty()) {
                JSONObject json = new JSONObject(string);
                for(int i=0;i<string.length();i++){
                    if(string.charAt(i)==(char)'}'){
                        if(i+2<string.length()) {
                            string = string.substring(i + 2);
                        }else{string = "";}
                        break;
                    }
                }
                jsonArray.put(json);

            }
            StationInfoInstance instance;
            for(int i=0;i<jsonArray.length();i++){
                instance = new StationInfoInstance(jsonArray.getJSONObject(i).getString("name") ,
                        "Bikes Available : "+(jsonArray.getJSONObject(i).getString("available_bikes")),
                        "Bikes Stands Available : "+(jsonArray.getJSONObject(i).getString("available_bike_stands")),0);


                if(!sharedPref.getString(instance.getName(),"").isEmpty()){
                    instance.setPinned(1);
                stationInfo.add(0,instance);}
                else{

                    stationInfo.add(instance);
                }
            }
        }catch(Exception e){
            Log.d(TAG, "parseJson: "+e);}
        Log.d(TAG, "parseJson: "+stationInfo);
        MySingleton.getInstance().setJsonArray(jsonArray);
        return stationInfo;
    }

}
