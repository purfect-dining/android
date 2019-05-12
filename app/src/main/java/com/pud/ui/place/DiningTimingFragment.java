package com.pud.ui.place;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.pud.R;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class DiningTimingFragment extends Fragment implements View.OnClickListener {

    private int currentChart;
    private BarChart mBarChart;
    private PieChart mPieChart;
    private Button mChartSwitch;


    public static DiningTimingFragment newInstance(int l, int m, int h) {
        DiningTimingFragment fragment = new DiningTimingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("l", l);
        bundle.putInt("m", m);
        bundle.putInt("h", h);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diningtiming, container, false);

        mBarChart = view.findViewById(R.id.barchart);
        mPieChart = view.findViewById(R.id.piechart);
        mChartSwitch = view.findViewById(R.id.chartSwitch);
        mChartSwitch.setOnClickListener(this);

        List<BarEntry> entries = new ArrayList<>();

        int l = getArguments().getInt("l");
        int m = getArguments().getInt("m");
        int h = getArguments().getInt("h");

        entries.add(new BarEntry(0, l));
        entries.add(new BarEntry(1, m));
        entries.add(new BarEntry(2, h));

        BarDataSet set1 = new BarDataSet(entries, "BarDataSet");
        set1.setColors(rgb("#e74c3c"), rgb("#f1c40f"), rgb("#2ecc71"));

        BarData data = new BarData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(10f);

        mBarChart.setFitBars(true);
        mBarChart.setData(data);
        mBarChart.getDescription().setEnabled(false);


        mPieChart.setCenterText("Rating");
        List<PieEntry> entriees = new ArrayList<>();

        entriees.add(new PieEntry(l, "Bad"));
        entriees.add(new PieEntry(m, "Okay"));
        entriees.add(new PieEntry(h, "Great"));

        PieDataSet set = new PieDataSet(entriees, "Election Results");
        set.setColors(rgb("#e74c3c"), rgb("#f1c40f"), rgb("#2ecc71"));

        PieData datap = new PieData(set);
        datap.setValueTextSize(15f);
        mPieChart.setData(datap);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (currentChart == 0) {
            currentChart = 1;
            mChartSwitch.setText("BAR CHART");
            mPieChart.setVisibility(View.VISIBLE);
            mBarChart.setVisibility(View.INVISIBLE);
        } else {
            currentChart = 0;
            mChartSwitch.setText("PIE CHART");
            mPieChart.setVisibility(View.INVISIBLE);
            mBarChart.setVisibility(View.VISIBLE);
        }
    }

}
