package io.insightworkshop.roofcareadmin.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.insightworkshop.roofcareadmin.R;
import io.insightworkshop.roofcareadmin.dashboard.fragments.profession.ProfessionsFragment;
import io.insightworkshop.roofcareadmin.dashboard.fragments.registeradmin.RegisterAdminFragment;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        uiInit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfessionsFragment()).commit();
        onNavAction();
    }

    private void onNavAction() {
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_professions) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfessionsFragment()).commit();
                return true;
            } else if (itemId == R.id.action_register) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new RegisterAdminFragment()).commit();
                return true;
            }
            return false;
        });
    }

    private void uiInit() {
        bottomNavView = findViewById(R.id.bottomNav);
    }
}