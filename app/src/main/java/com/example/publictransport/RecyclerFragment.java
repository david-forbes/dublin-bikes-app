package com.example.publictransport;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.min;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
    public ViewGroup container_holder;
    public ArrayList<StationInfoInstance> stationInfo = new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Dublin Bikes");

        RecyclerView recyclerView = view.findViewById(R.id.rvStations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new MyRecyclerViewAdapter(getActivity(), stationInfo);
        setHasOptionsMenu(true);


        recyclerView.setAdapter(adapter);

        adapter.setClickListener(this::onItemClick);
        adapter.setLongClickListener(this::onItemLongClick);

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





        apiRequest.execute();






    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.options_menu,menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.filterList("");

                } else {
                    adapter.filterList(newText);
                }
                return true;
            }
        });


    }

    public void onItemClick(View view, int position) {
        Toast.makeText(MyApplication.getAppContext(), "Station has been added to favourites", Toast.LENGTH_SHORT).show();
        stationInfo.get(position).setPinned(1);
        StationInfoInstance stationInfoInstance = stationInfo.get(position);
        stationInfo.remove(position);
        stationInfo.add(0,stationInfoInstance);
        adapter.notifyDataSetChanged();


        Context context = MyApplication.getAppContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.publictransport",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(stationInfo.get(0).getName(), "true");
        editor.apply();
    }
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: ");


        Toast.makeText(MyApplication.getAppContext(), "Station has been removed from favourites", Toast.LENGTH_SHORT).show();
        stationInfo.get(position).setPinned(0);
        StationInfoInstance stationInfoInstance = stationInfo.get(position);
        stationInfo.remove(position);
        stationInfo.add(stationInfo.size()-1,stationInfoInstance);
        adapter.notifyDataSetChanged();


        Context context = MyApplication.getAppContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.publictransport",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(stationInfo.get(0).getName(), "");
        editor.apply();


    }

}


