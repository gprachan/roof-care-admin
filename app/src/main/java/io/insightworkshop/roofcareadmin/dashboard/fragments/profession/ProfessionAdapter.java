package io.insightworkshop.roofcareadmin.dashboard.fragments.profession;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import io.insightworkshop.roofcareadmin.R;
import io.insightworkshop.roofcareadmin.api.Apis;
import io.insightworkshop.roofcareadmin.model.ProfessionResponse;

public class ProfessionAdapter extends RecyclerView.Adapter<ProfessionAdapter.MyViewHolder> {
    private final ProfessionResponse profession;

    public ProfessionAdapter(ProfessionResponse response) {
        this.profession = response;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_profession_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.professionName.setText(profession.getProfessions().get(position).getProfessionName());
        holder.btnEdit.setOnClickListener(v -> {
            deleteApiCall(profession.getProfessions().get(position).getProfessionId(), position, holder.itemView.getContext());
        });
    }

    private void deleteApiCall(int professionId, int position, Context context) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    Apis.deleteProfession + professionId,
                    response -> {
                        try {
                            if (response.equalsIgnoreCase("true")) {
                                profession.getProfessions().remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, profession.getProfessions().size());
                            } else {
                                Toast.makeText(context, "Failed to delete profession!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            );
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
        } catch (Exception ex) {
            Log.e("tag", ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return profession.getProfessions().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView professionName;
        private final Button btnEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            professionName = itemView.findViewById(R.id.tvProfessionName);
            btnEdit = itemView.findViewById(R.id.btnDelete);
        }
    }
}
