package com.pud.ui.place;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pud.model.Place;

public class DiningTimingPagerAdapter extends FragmentPagerAdapter {

    private Place mPlace;

    public DiningTimingPagerAdapter(FragmentManager fm, Place place) {
        super(fm);
        this.mPlace = place;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            int l = 0, m = 0, h = 0;
            for (int i = 0; i < mPlace.getDiningTimings().size(); i++) {
                for (int j = 0; j < mPlace.getDiningTimings().get(i).getRatings().size(); j++) {
                    if (mPlace.getDiningTimings().get(i).getRatings().get(j).getRating() == 1) l++;
                    else if (mPlace.getDiningTimings().get(i).getRatings().get(j).getRating() == 2)
                        m++;
                    else if (mPlace.getDiningTimings().get(i).getRatings().get(j).getRating() == 3)
                        h++;
                }
            }
            return DiningTimingFragment.newInstance(l, m, h);
        } else
            return DiningTimingMenuFragment.newInstance(mPlace.getDiningTimings().get(position));
    }

    @Override
    public int getCount() {
        return mPlace.getDiningTimings().size();
    }

}