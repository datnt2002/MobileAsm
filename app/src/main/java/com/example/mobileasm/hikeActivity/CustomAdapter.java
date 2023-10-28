package com.example.mobileasm.hikeActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileasm.MyDatabaseHelper;
import com.example.mobileasm.R;
import com.example.mobileasm.models.HikeModel;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HikeModel> hikeLists;
    Activity activity;
    public CustomAdapter(Activity activity, Context context, ArrayList<HikeModel> hikeLists) {
        this.activity = activity;
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
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HikeModel hike = hikeLists.get(position);

        holder.idTextView.setText(String.valueOf(hike.getHikeId()));
        holder.hikeNameTextView.setText(hike.getHikeName());
        holder.hikeLocationTextView.setText(hike.getHikeLocation());
        holder.hikeLengthTextView.setText(String.valueOf(hike.getHikeLength()));
        holder.hikeDateTextView.setText(String.valueOf(hike.getHikeDate()));
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                int id = hike.getHikeId();
                intent.putExtra("id", id);
                activity.startActivityForResult(intent, 1);
            }
        });
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                int id = hike.getHikeId();
                intent.putExtra("id", id);
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = hike.getHikeId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete this hike?");
                builder.setMessage("Are you sure to delete this hike");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int positionToDelete = holder.getAdapterPosition(); // Get the current position within the adapter
                        if (positionToDelete != RecyclerView.NO_POSITION) {
                            MyDatabaseHelper myDb = new MyDatabaseHelper(context);
                            myDb.deleteData(id);
                            hikeLists.remove(positionToDelete);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikeLists.size();
    }


    public void setFilteredList(ArrayList<HikeModel> filteredList){
        this.hikeLists = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView idTextView, hikeNameTextView, hikeLocationTextView, hikeLengthTextView, hikeDateTextView;
        RelativeLayout mainLayout;
        ImageView editBtn, deleteBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.id_in_list_card);
            hikeNameTextView = itemView.findViewById(R.id.hike_name_in_list_card);
            hikeLocationTextView = itemView.findViewById(R.id.hike_location_in_list_card);
            hikeLengthTextView = itemView.findViewById(R.id.hike_length_in_list_card);
            hikeDateTextView = itemView.findViewById(R.id.hike_date_in_list_card);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            editBtn = itemView.findViewById(R.id.edit_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
