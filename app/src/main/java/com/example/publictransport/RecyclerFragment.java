package com.example.publictransport;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.min;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{
    public String TAG = RecyclerFragment.class.getSimpleName();
    public MyRecyclerViewAdapter adapter;

    public ArrayList<StationInfoInstance> stationInfo = new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvStations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new MyRecyclerViewAdapter(getActivity(), stationInfo);


        recyclerView.setAdapter(adapter);

        adapter.setClickListener(this::onItemClick);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        @SuppressLint("StaticFieldLeak") ApiRequest apiRequest = new ApiRequest() {

            protected void onPostExecute(Object jsonObject) {
                super.onPostExecute(jsonObject);
                stationInfo = (ArrayList<StationInfoInstance>) jsonObject;
                Log.d(TAG, "recyclerfragment: "+stationInfo);
                    adapter.setFilterList(stationInfo);
                    adapter.setList(stationInfo);
                    adapter.notifyDataSetChanged();
                    //adapter.filterList("cl");


                }





        };



        // set up the RecyclerView

        apiRequest.execute();



    }

    public void onItemClick(View view, int position) {
        StationInfoInstance stationInfoInstance = stationInfo.get(position);
        stationInfo.remove(position);
        stationInfo.add(0,stationInfoInstance);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Context context = MyApplication.getAppContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.publictransport",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(stationInfo.get(0).getName(), "true");
        editor.apply();
    }

}


