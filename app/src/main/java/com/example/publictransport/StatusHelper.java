package com.example.publictransport;

import android.app.Activity;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class StatusHelper {
    public static String TAG = StatusHelper.class.getSimpleName();
    public static ArrayList getBikes(){
        ArrayList stationBikes = new ArrayList();




        return stationBikes;

    }

    public static JSONObject queryApi() {


                JSONObject requestResponse = null;

                try {
                    URL url = new URL("https://data.smartdublin.ie/dublinbikes-api");

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        StringBuilder responseStrBuilder = new StringBuilder();

                        String inputStr;
                        while ((inputStr = streamReader.readLine()) != null)
                            responseStrBuilder.append(inputStr);
                        requestResponse = new JSONObject(responseStrBuilder.toString());
                        Log.d(TAG, "queryApi: " + requestResponse);

                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "queryApi: " + e);
                }
                return requestResponse;



    }
    public static ArrayList<StationInfoInstance> parseJson(String stringObject) {
        String string=stringObject.toString();
        ArrayList<StationInfoInstance> stationInfo= new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
        string = string.substring(1, string.length() - 1);
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
                stationInfo.add(instance);
            }
    }catch(Exception e){
            Log.d(TAG, "parseJson: "+e);}
        return stationInfo;
    }
}
