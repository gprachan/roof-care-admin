package io.insightworkshop.roofcareadmin.dashboard.fragments.registeradmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.insightworkshop.roofcareadmin.R;

public class RegisterAdminFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regsiter_admin, container, false);
    }
}