package com.pud.listener;

import androidx.annotation.Px;
import androidx.viewpager.widget.ViewPager;

public interface ViewPagerListener extends ViewPager.OnPageChangeListener {

    default void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) {

    }

    default void onPageScrollStateChanged(int state) {

    }

}
