package com.example.mobileasm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HikeModel> hikeLists;

    public CustomAdapter(Context context, ArrayList<HikeModel> hikeLists) {
        this.context = context;
        this.hikeLists = hikeLists;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        HikeModel hike = hikeLists.get(position);

        holder.idTextView.setText(String.valueOf(hike.getHikeId()));
        holder.hikeNameTextView.setText(hike.getHikeName());
        holder.hikeLocationTextView.setText(hike.getHikeLocation());
        holder.hikeLengthTextView.setText(String.valueOf(hike.getHikeLength()));
        holder.hikeDateTextView.setText(String.valueOf(hike.getHikeDate()));

    }

    @Override
    public int getItemCount() {
        return hikeLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView idTextView, hikeNameTextView, hikeLocationTextView, hikeLengthTextView, hikeDateTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.id_in_list_card);
            hikeNameTextView = itemView.findViewById(R.id.hike_name_in_list_card);
            hikeLocationTextView = itemView.findViewById(R.id.hike_location_in_list_card);
            hikeLengthTextView = itemView.findViewById(R.id.hike_length_in_list_card);
            hikeDateTextView = itemView.findViewById(R.id.hike_date_in_list_card);
        }
    }
}
