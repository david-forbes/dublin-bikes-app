package com.example.publictransport;

import static java.lang.Math.min;

import android.annotation.SuppressLint;
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

            protected void onPostExecute(Object stringObject) {
                super.onPostExecute(stringObject);
                String string=stringObject.toString();


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
                    MySingleton.getInstance().setJsonArray(jsonArray);
                    adapter.notifyDataSetChanged();


                    Log.d(TAG, "onPostExecute: " + jsonArray.length());
                }catch (Exception e){
                    Log.d(TAG, "onPostExecute: "+e);
                }


            }



        };



        // set up the RecyclerView

        apiRequest.execute();



    }

    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}


