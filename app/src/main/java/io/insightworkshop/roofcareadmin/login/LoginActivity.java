package io.insightworkshop.roofcareadmin.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

import io.insightworkshop.roofcareadmin.R;
import io.insightworkshop.roofcareadmin.api.Apis;
import io.insightworkshop.roofcareadmin.dashboard.DashboardActivity;
import io.insightworkshop.roofcareadmin.model.LoginResponse;
import io.insightworkshop.roofcareadmin.sshSolve.HttpsTrustManager;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextInputEditText username, password;
    private TextInputLayout uName, pWord;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HttpsTrustManager.allowAllSSL();
        getSupportActionBar().hide();
        initUI();
        onLogin();
    }

    private void onLogin() {
        loginBtn.setOnClickListener(v -> {
            if (!isValidForm()) return;
            loginUserApiCall();
        });
    }

    private void loginUserApiCall() {
        try {
            loginBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Username", username.getText().toString());
            jsonBody.put("Password", password.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    Apis.logInAuthentication,
                    jsonBody,
                    response -> {
                        loginBtn.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        try {
                            Gson gson = new GsonBuilder().create();
                            LoginResponse authenticationResponse = gson.fromJson(String.valueOf(response), LoginResponse.class);
                            if (authenticationResponse != null) {
                                startActivity(new Intent(this, DashboardActivity.class));
                                finish();
                            } else {
                                uName.setError("Invalid credentials were provided!");
                            }
                        } catch (Exception ex) {
                            Log.e("TAG", ex.getMessage());
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        loginBtn.setEnabled(true);
                        Toast.makeText(this, "Error Invalid credentials were provided!", Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isValidForm() {
        if (username.getText().toString().trim().isEmpty()) {
            username.requestFocus();
            uName.setError("Username can't be empty!");
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            pWord.setError("Password can't be empty!");
            password.requestFocus();
            uName.setError(null);
            return false;
        } else {
            pWord.setError(null);
            return true;
        }
    }

    private void initUI() {
        loginBtn = findViewById(R.id.buttonLogIn);
        username = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        uName = findViewById(R.id.name_text_input_phoneNumber);
        pWord = findViewById(R.id.name_text_input_password);
        progressBar = findViewById(R.id.progressBar);
    }
}