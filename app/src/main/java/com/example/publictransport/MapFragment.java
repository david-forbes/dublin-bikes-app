package com.example.publictransport;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

public class MapFragment extends Fragment implements  OnMapReadyCallback{

    String TAG = MapFragment.class.getSimpleName();
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMapView.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    /*
    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
        Log.d(TAG, "onSaveInstanceState: ");
    }

     */




    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap mMap) {
        GoogleMap googleMap = mMap;


        JSONArray jsonArray = MySingleton.getInstance().getJsonArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Double lon = Double.parseDouble(jsonArray.getJSONObject(i).getString("longitude"));
                Double lat = Double.parseDouble(jsonArray.getJSONObject(i).getString("latitude"));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(jsonArray.getJSONObject(i).getString("name")));

            } catch (Exception e) {
            }
        }



        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        LatLng dublin = new LatLng(53.350140, -6.266155);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(dublin).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


}






