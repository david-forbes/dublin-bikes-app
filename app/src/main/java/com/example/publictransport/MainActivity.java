package com.example.publictransport;

import static java.lang.Math.min;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    public String TAG = MainActivity.class.getSimpleName();
    public MyRecyclerViewAdapter adapter;
    public Menu topMenu;
    public ArrayList<StationInfoInstance> stationInfo = new ArrayList<>();


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
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

                getSupportFragmentManager().beginTransaction().replace(R.id.container,mapFragment).commit();


                return true;
        }

        return true;

    }



    public void onMapReady(GoogleMap googleMap) {
        JSONArray jsonArray = MySingleton.getInstance().getJsonArray();

}

}
