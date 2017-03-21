package com.ebnbin.gank.feature.days;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ebnbin.gank.feature.day.DayFragment;

final class DaysPagerAdapter extends FragmentPagerAdapter {
    DaysPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new DayFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
