package com.example.publictransport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<StationInfoInstance> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<StationInfoInstance> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {

        String stationName = mData.get(position).getName();
        String bikesAvailable = mData.get(position).getBikes_available();
        String bikeStandsAvailable=mData.get(position).getAvailable_bike_stands();
        holder.tvBikesAvailable.setText(bikesAvailable);
        holder.tvStationName.setText(stationName);
        holder.tvBikeStandsAvailable.setText(bikeStandsAvailable);
    }

    // binds the data to the TextView in each row



    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvBikesAvailable;
        TextView tvStationName;
        TextView tvBikeStandsAvailable;

        ViewHolder(View itemView) {
            super(itemView);
            tvStationName = itemView.findViewById(R.id.tvStationName);
            tvBikesAvailable = itemView.findViewById(R.id.tvBikesAvailable);
            tvBikeStandsAvailable = itemView.findViewById(R.id.tvBikeStandsAvailable);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getName();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
