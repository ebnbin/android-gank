package com.ebnbin.gank.feature.days;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.ebnbin.gank.feature.day.DayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Days {@link PagerAdapter}.
 */
final class DaysPagerAdapter extends FragmentPagerAdapter {
    public final List<int[]> mDaysHistoryList = new ArrayList<>();

    DaysPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * Sets new data.
     */
    public void setDaysHistoryList(@Nullable List<int[]> daysHistoryList) {
        mDaysHistoryList.clear();

        if (daysHistoryList != null) {
            mDaysHistoryList.addAll(daysHistoryList);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        int[] date = mDaysHistoryList.get(position);
        return DayFragment.newInstance(date);
    }

    @Override
    public int getCount() {
        return mDaysHistoryList.size();
    }
}
