package io.insightworkshop.roofcareadmin.dashboard.fragments.profession;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import io.insightworkshop.roofcareadmin.R;
import io.insightworkshop.roofcareadmin.api.Apis;
import io.insightworkshop.roofcareadmin.model.ProfessionResponse;

public class ProfessionsFragment extends Fragment {
    private RecyclerView rvProfessions;
    private TextInputEditText profession;
    private TextInputLayout tilProfessionName;
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_professions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiInit(view);
        professionApiCall();
        onAddClick();
    }

    private void onAddClick() {
        btnAdd.setOnClickListener(v -> {
            if (profession.getText().toString().isEmpty()) {
                tilProfessionName.setErrorEnabled(true);
                tilProfessionName.setError("Profession name is required!");
                profession.requestFocus();
            } else {
                tilProfessionName.setErrorEnabled(false);
                tilProfessionName.setError(null);
                postProfessionApiCall();
            }
        });
    }

    private void postProfessionApiCall() {
        try {
            btnAdd.setEnabled(false);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Apis.addProfession + profession.getText().toString(),
                    response -> {
                        btnAdd.setEnabled(true);
                        try {
                            if (response.equals("true")) {
                                professionApiCall();
                                profession.setText("");
                                Toast.makeText(getContext(), "Profession Added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Profession Exists", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Log.e("TAG", "postProfessionApiCall:" + ex.getMessage());
                        }
                    },
                    error -> {
                        btnAdd.setEnabled(true);
                        Toast.makeText(getContext(), "Failed to add profession!", Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(request);
        } catch (Exception ex) {
            Log.e("tag", ex.getMessage());
        }
    }

    private void professionApiCall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Apis.getProfessions,
                    response -> {
                        try {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            ProfessionResponse professionResponse = new GsonBuilder().create().fromJson(response, ProfessionResponse.class);
                            if (professionResponse != null) {
                                populateRecyclerView(professionResponse);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } catch (Exception ex) {
            Log.e("TAG", ex.getMessage());
        }
    }

    private void populateRecyclerView(ProfessionResponse professionResponse) {
        rvProfessions.setLayoutManager(new LinearLayoutManager(getContext()));
        ProfessionAdapter adapter = new ProfessionAdapter(professionResponse);
        rvProfessions.setAdapter(adapter);
    }

    private void uiInit(View view) {
        rvProfessions = view.findViewById(R.id.rvProfessions);
        profession = view.findViewById(R.id.tieProfession);
        btnAdd = view.findViewById(R.id.btnAdd);
        tilProfessionName = view.findViewById(R.id.tilProfessionName);
    }
}