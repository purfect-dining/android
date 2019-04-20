package com.pud.ui.place;

import com.pud.model.Place;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DiningTimingPagerAdapter extends FragmentPagerAdapter {

    private Place mPlace;

    public DiningTimingPagerAdapter(FragmentManager fm, Place place) {
        super(fm);
        this.mPlace = place;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new DiningTimingFragment();
//            return DiningTimingFragment.newInstance(mPlace.getObjectId());
        else
            return DiningTimingMenuFragment.newInstance(mPlace.getDiningTimings().get(position));
    }

    @Override
    public int getCount() {
        return mPlace.getDiningTimings().size();
    }

}