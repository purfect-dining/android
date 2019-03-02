package com.pud.ui.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pud.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DiningTimingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_diningtiming, container, false);

        return rootView;
    }
}
