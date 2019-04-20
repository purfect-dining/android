package com.pud.ui.place;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.DiningTiming;
import com.pud.model.MenuItem;
import com.pud.model.MenuSection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DiningTimingMenuFragment extends Fragment {

    public static DiningTimingMenuFragment newInstance(DiningTiming diningTiming) {
        DiningTimingMenuFragment fragment = new DiningTimingMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("diningTiming", diningTiming);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diningtiming_menu, container, false);
        DiningTiming diningTiming = getArguments().getParcelable("diningTiming");
        String s = diningTiming.getDiningType().getName() + "\n" +
                diningTiming.getFrom().toString().substring(11, 16) + " - " +
                diningTiming.getTo().toString().substring(11, 16);
        ((TextView) view.findViewById(R.id.diningtiming_text)).setText(s);
        LinearLayout layout = view.findViewById(R.id.menu);
        for (MenuSection section : diningTiming.getMenuSections()) {
            TextView ts = new TextView(getContext());
            ts.setText(section.getName());
            ts.setTextSize(20);
            ts.setPadding(0, 10, 0, 0);
            layout.addView(ts);
            for (MenuItem item : section.getMenuItems()) {
                TextView ti = new TextView(getContext());
                ti.setText(item.getName());
                layout.addView(ti);
            }
        }
        return view;
    }
}
