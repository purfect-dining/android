package com.pud.ui.place;

import com.pud.model.DiningTiming;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DiningTimingPagerAdapter extends FragmentPagerAdapter {

    private List<DiningTiming> mTimings;

    public DiningTimingPagerAdapter(FragmentManager fm, List<DiningTiming> timings) {
        super(fm);
        this.mTimings = timings;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return DiningTimingFragment.newInstance(mTimings.get(position));
    }

    @Override
    public int getCount() {
        return mTimings.size();
    }

}