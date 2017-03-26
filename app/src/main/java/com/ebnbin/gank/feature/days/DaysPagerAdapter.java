package com.ebnbin.gank.feature.days;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.ebnbin.eb.util.Date;
import com.ebnbin.gank.feature.day.DayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Days {@link PagerAdapter}.
 */
final class DaysPagerAdapter extends FragmentPagerAdapter {
    public final List<Date> mDaysHistoryList = new ArrayList<>();

    DaysPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * Sets new data.
     */
    public void setHistoryModel(@NonNull HistoryModel historyModel) {
        setDaysHistoryList(DaysUtil.getDaysHistoryList(historyModel));
    }

    /**
     * Sets new data.
     */
    private void setDaysHistoryList(@Nullable List<Date> daysHistoryList) {
        mDaysHistoryList.clear();

        if (daysHistoryList != null) {
            mDaysHistoryList.addAll(daysHistoryList);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Date date = mDaysHistoryList.get(position);
        return DayFragment.newInstance(date);
    }

    @Override
    public int getCount() {
        return mDaysHistoryList.size();
    }
}
