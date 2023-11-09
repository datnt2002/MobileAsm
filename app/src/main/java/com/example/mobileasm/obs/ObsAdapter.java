package com.example.mobileasm.obs;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.mobileasm.models.ObservationsModel;
import java.util.ArrayList;
public class ObsAdapter extends RecyclerView.Adapter<ObsAdapter.ObsViewHolder>{
    private Context context;
    private ArrayList<ObservationsModel> obsLists;
    Activity activity;
    public ObsAdapter( Activity activity, Context context, ArrayList<ObservationsModel> obsLists) {
        this.context = context;
        this.obsLists = obsLists;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ObsAdapter.ObsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.obs_row, parent, false);
        return new ObsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ObsAdapter.ObsViewHolder holder, int position) {
        ObservationsModel obs = obsLists.get(position);
        holder.obsId.setText(String.valueOf(obs.getObsId()));
        holder.obsNameTextView.setText(obs.getObsName());
        holder.obsDateTextView.setText(obs.getObsDate());
        byte[] imageData = obs.getObsImage();
        if (imageData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            holder.imageObsView.setImageBitmap(bitmap);
        } else {
            // Set a default image if the avatar data is null
            holder.imageObsView.setImageResource(R.drawable.ic_image);
        }
        holder.obsListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailObsActivity.class);
                int obsId = obs.getObsId();
                intent.putExtra("obsId", obsId);
                activity.startActivity(intent);
            }
        });
        holder.editObsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateObsActivity.class);
                int id = obs.getObsId();
                intent.putExtra("obsId", id);
                activity.startActivityForResult(intent, 1);
            }
        });
        holder.deleteObsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = obs.getObsId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete this observation?");
                builder.setMessage("Are you sure to delete this observation");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int positionToDelete = holder.getAdapterPosition(); // Get the current position within the adapter
                        if (positionToDelete != RecyclerView.NO_POSITION) {
                            MyDatabaseHelper myDb = new MyDatabaseHelper(context);
                            myDb.deleteObservation(id);
                            obsLists.remove(positionToDelete);
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
        return obsLists.size();
    }
    public class ObsViewHolder extends RecyclerView.ViewHolder{
        TextView obsId, obsNameTextView, obsDateTextView;
        RelativeLayout obsListLayout;
        ImageView editObsBtn, deleteObsBtn, imageObsView;
        public ObsViewHolder(@NonNull View itemView) {
            super(itemView);
            obsId = itemView.findViewById(R.id.obs_id);
            obsNameTextView = itemView.findViewById(R.id.obs_name_in_list_card);
            obsDateTextView = itemView.findViewById(R.id.obs_date_in_list_card);
            obsListLayout = itemView.findViewById(R.id.obsListLayout);
            editObsBtn = itemView.findViewById(R.id.edit_obs_btn);
            deleteObsBtn = itemView.findViewById(R.id.delete_obs_btn);
            imageObsView = itemView.findViewById(R.id.obs_image_in_list);
        }
    }
}
