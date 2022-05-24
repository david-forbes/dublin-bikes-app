package com.example.publictransport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private ArrayList<StationInfoInstance> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;
    private MyFilter filter;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<StationInfoInstance> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.filter = new MyFilter(data,this);
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
        if (mData.get(position).getPinned()==1){
        holder.cardView.setBackgroundColor(MyApplication.getAppContext().getResources().getColor(R.color.teal_700));
    }else{
            holder.cardView.setBackgroundColor(MyApplication.getAppContext().getResources().getColor(R.color.white));
        }
    }

    // binds the data to the TextView in each row
    public List<StationInfoInstance> getList() {
        return mData;
    }

    public void setList(ArrayList<StationInfoInstance> mData) {
        this.mData = mData;
        this.notifyDataSetChanged();
    }
    public void setFilterList(ArrayList<StationInfoInstance> mData){
        this.filter.setStationInfoInstanceArrayList(mData);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(String text) {
        filter.performFiltering(text);
    }


    // stores and recycles views as they are scrolled off screen

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView tvBikesAvailable;
        TextView tvStationName;
        TextView tvBikeStandsAvailable;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            tvStationName = itemView.findViewById(R.id.tvStationName);
            tvBikesAvailable = itemView.findViewById(R.id.tvBikesAvailable);
            tvBikeStandsAvailable = itemView.findViewById(R.id.tvBikeStandsAvailable);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(this::onClick);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this::onLongClick);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) mLongClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
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
    void setLongClickListener(ItemLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

}
