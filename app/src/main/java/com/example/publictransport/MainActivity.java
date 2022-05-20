package com.example.publictransport;

import static java.lang.Math.min;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    public String TAG = MainActivity.class.getSimpleName();
    public MyRecyclerViewAdapter adapter;

    public ArrayList<StationInfoInstance> stationInfo = new ArrayList<>();


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_1);

    }

    RecyclerFragment recyclerFragment = new RecyclerFragment();
    MapFragment mapFragment = new MapFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.page_1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, recyclerFragment).commit();
                return true;

            case R.id.page_2:
                //SupportMapFragment mapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,mapFragment).commit();
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                /*
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapView);
                supportMapFragment.getMapAsync(this);

                 */
                return true;
        }



/*
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;

 */
        return true;

    }

    public void onMapReady(GoogleMap googleMap) {
        JSONArray jsonArray = MySingleton.getInstance().getJsonArray();
        /*
        for(int i =0;i<3;i++){
            try {
                Double lon = Double.parseDouble(jsonArray.getJSONObject(i).getString("longitude"));
                Double lat = Double.parseDouble(jsonArray.getJSONObject(i).getString("latitude"));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(jsonArray.getJSONObject(i).getString("name")));
            }catch (Exception e){
                Log.d(TAG, "onMapReady: "+e);


    }*/
}

}
/*
        @SuppressLint("StaticFieldLeak") ApiRequest apiRequest = new ApiRequest() {

            protected void onPostExecute(Object stringObject) {
                super.onPostExecute(stringObject);
                String string=stringObject.toString();
                Log.d(TAG, "onPostExecute: testing");
                Log.d(TAG, "onPostExecute: "+string);
                for(int i=0;i<(string.length()/4000)+1;i++){

                    Log.d(TAG, "onPostExecute"+string.substring(i*4000,min(string.length(),(i+1)*4000)));
                }
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
                        instance = new StationInfoInstance("Station Name : "+jsonArray.getJSONObject(i).getString("name") ,
                                "Bikes Available : "+(jsonArray.getJSONObject(i).getString("available_bikes")),
                                "Bikes Stands Available : "+(jsonArray.getJSONObject(i).getString("available_bike_stands")));
                        stationInfo.add(instance);
                    }
                    adapter.notifyDataSetChanged();


                    Log.d(TAG, "onPostExecute: " + jsonArray.length());
                }catch (Exception e){
                    Log.d(TAG, "onPostExecute: "+e);
                }


            }



        };



        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvStations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, stationInfo);


        recyclerView.setAdapter(adapter);

        adapter.setClickListener(this::onItemClick);
        apiRequest.execute();



    }

    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}*/