package com.ebnbin.gank.feature.daysviewpager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.ebnbin.eb.util.Date;
import com.ebnbin.gank.feature.day.DayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Days {@link PagerAdapter}.
 */
final class DaysViewPagerPagerAdapter extends FragmentStatePagerAdapter {
    public final List<Date> dates = new ArrayList<>();

    DaysViewPagerPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * Sets new data.
     */
    public void setHistoryModel(@NonNull HistoryModel historyModel) {
        setDates(DaysViewPagerUtil.getDaysHistoryList(historyModel));
    }

    /**
     * Sets new data.
     */
    private void setDates(@Nullable List<Date> dates) {
        this.dates.clear();

        if (dates != null) {
            this.dates.addAll(dates);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Date date = dates.get(position);
        return DayFragment.newInstance(date);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}