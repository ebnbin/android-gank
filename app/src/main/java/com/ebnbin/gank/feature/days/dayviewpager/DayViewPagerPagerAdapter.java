package com.ebnbin.gank.feature.days.dayviewpager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ebnbin.eb.util.Date;
import com.ebnbin.gank.feature.days.day.DayFragment;
import com.ebnbin.gank.feature.days.day.DayModel;

import java.util.ArrayList;

/**
 * Days {@link android.support.v4.view.PagerAdapter}.
 */
final class DayViewPagerPagerAdapter extends FragmentStatePagerAdapter {
    public final ArrayList<Date> dates = new ArrayList<>();

    private final SparseArray<DayFragment> mDayFragmentSparseArray = new SparseArray<>();

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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DayFragment dayFragment = (DayFragment) super.instantiateItem(container, position);
        mDayFragmentSparseArray.put(position, dayFragment);

        return dayFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mDayFragmentSparseArray.remove(position);

        super.destroyItem(container, position, object);
    }

    @Nullable
    public DayFragment getDayFragment(int position) {
        return mDayFragmentSparseArray.get(position);
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public static abstract class FuliUrlCallback {
        public void onGetFuliUrl(@Nullable String fuliUrl) {
        }
    }

    public void getFuliUrl(int position, @NonNull FuliUrlCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DayFragment dayFragment = getDayFragment(position);
                if (dayFragment == null) {
                    mHandler.post(this);

                    return;
                }

                dayFragment.getDayModel(new DayFragment.DayModelCallback() {
                    @Override
                    public void onGetDayModel(@NonNull DayModel dayModel) {
                        super.onGetDayModel(dayModel);

                        String fuliUrl = dayModel.getResults().getValidFuliUrl();
                        callback.onGetFuliUrl(fuliUrl);
                    }
                });
            }
        });
    }
}
