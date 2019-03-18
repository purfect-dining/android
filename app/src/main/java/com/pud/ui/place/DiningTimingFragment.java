package com.pud.ui.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.DiningTiming;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DiningTimingFragment extends Fragment {

    public static DiningTimingFragment newInstance(DiningTiming diningTiming) {
        DiningTimingFragment fragment = new DiningTimingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("diningTiming", diningTiming);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diningtiming, container, false);
        ((TextView) view.findViewById(R.id.diningtiming_text)).setText(((DiningTiming) getArguments().getParcelable("diningTiming")).getDiningType().getName());
        return view;
    }
}
