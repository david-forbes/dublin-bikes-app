package com.example.publictransport;

import android.app.Activity;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}
