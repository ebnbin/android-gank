package com.ebnbin.gank.feature.days.dayviewpager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ebnbin.eb.util.Date;
import com.ebnbin.gank.feature.days.day.DayFragment;

import java.util.ArrayList;

/**
 * Days {@link android.support.v4.view.PagerAdapter}.
 */
final class DayViewPagerPagerAdapter extends FragmentStatePagerAdapter {
    public final ArrayList<Date> dates = new ArrayList<>();

    DayViewPagerPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * Sets new data.
     */
    public void setHistoryModel(@NonNull HistoryModel historyModel) {
        setDates(DayViewPagerUtil.getDaysHistoryList(historyModel));
    }

    /**
     * Sets new data.
     */
    private void setDates(@Nullable ArrayList<Date> dates) {
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
