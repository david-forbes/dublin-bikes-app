package com.example.publictransport;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;

public class ApiRequest extends AsyncTask {
    public static String TAG = ApiRequest.class.getSimpleName();

    @Override
    protected String doInBackground(Object[] objects) {
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
                //Log.d(TAG, "doInBackground: "+responseStrBuilder);
                requestResponse = responseStrBuilder.toString();
                //Log.d(TAG, "doInBackground: "+responseStrBuilder.toString());

                //Log.d(TAG, "queryApi: " + requestResponse.length());

            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.d(TAG, "queryApi: " + e);
        }
        return requestResponse;
    }

}
