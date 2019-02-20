package com.pud.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pud.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RecoveryFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_auth_recovery, container, false);
        return rootView;
    }

}
