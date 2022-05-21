package com.example.publictransport;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

class MyFilter{

    public ArrayList<StationInfoInstance> stationInfoInstanceArrayList;
    public ArrayList<StationInfoInstance> filteredStationInfoInstanceArrayList;
    private MyRecyclerViewAdapter adapter;
    public String TAG = MyFilter.class.getSimpleName();


    public MyFilter(ArrayList<StationInfoInstance> stationInfoInstanceArrayList, MyRecyclerViewAdapter adapter) {
        this.stationInfoInstanceArrayList = stationInfoInstanceArrayList;

        this.filteredStationInfoInstanceArrayList = new ArrayList<>();
        this.adapter = adapter;
    }


    public ArrayList<StationInfoInstance> getStationInfoInstanceArrayList() {
        return stationInfoInstanceArrayList;
    }

    public void setStationInfoInstanceArrayList(ArrayList<StationInfoInstance> stationInfoInstanceArrayList) {
        this.stationInfoInstanceArrayList = stationInfoInstanceArrayList;
    }

    protected void performFiltering(String searchString) {
        Log.d(TAG, "MyFilter: "+stationInfoInstanceArrayList);
        filteredStationInfoInstanceArrayList.clear();


        //here you need to add proper items do filteredContactList
        for (final StationInfoInstance item : stationInfoInstanceArrayList) {
            if (item.getName().toLowerCase().trim().contains(searchString)) {
                filteredStationInfoInstanceArrayList.add(item);
                Log.d(TAG, "performFiltering: "+item.getName());
            }
        }
        adapter.setList(filteredStationInfoInstanceArrayList);
        adapter.notifyDataSetChanged();

    }



}

