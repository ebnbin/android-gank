package com.ebnbin.gank.feature.days;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebnbin.eb.util.Date;
import com.ebnbin.ebapplication.base.EBFragment;
import com.ebnbin.ebapplication.net.NetCallback;
import com.ebnbin.gank.R;
import com.ebnbin.gank.feature.day.DayFragment;

/**
 * 用 {@link ViewPager} 展示多个 {@link DayFragment}.
 */
public final class DaysFragment extends EBFragment {
    private ViewPager mDaysViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.days_fragment, container, false);

        mDaysViewPager = (ViewPager) rootView.findViewById(R.id.days);

        return rootView;
    }

    private DaysPagerAdapter mDaysPagerAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDaysPagerAdapter = new DaysPagerAdapter(getChildFragmentManager());
        mDaysViewPager.setAdapter(mDaysPagerAdapter);

        mDaysViewPager.setOffscreenPageLimit(2);

        mDaysViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * Sets title of {@link ActionBar}.
     */
    private void setTitle(int position) {
        AppCompatActivity appCompatActivity = getAppCompatActivity();
        if (appCompatActivity == null) {
            return;
        }

        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        Date date = mDaysPagerAdapter.dates.get(position);
        if (date == null) {
            return;
        }

        String title = getString(R.string.days_title, date.year, date.month, date.day);
        actionBar.setTitle(title);
    }

    //*****************************************************************************************************************
    // Instance state.

    private static final String STATE_HISTORY_MODEL = "history_model";
    private static final String STATE_CURRENT_ITEM = "current_item";

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (!restoreHistory(savedInstanceState)) {
            netGetHistory();
        }
    }

    /**
     * 恢复 {@link #mHistoryModel}.
     *
     * @return 是否成功恢复.
     */
    public boolean restoreHistory(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }

        mHistoryModel = (HistoryModel) savedInstanceState.getSerializable(STATE_HISTORY_MODEL);
        if (mHistoryModel == null) {
            return false;
        }

        mDaysPagerAdapter.setHistoryModel(mHistoryModel);

        int defaultCurrentItem = mDaysPagerAdapter.getCount() - 1;
        int currentItem = savedInstanceState.getInt(STATE_CURRENT_ITEM, defaultCurrentItem);
        if (currentItem >= 0 && currentItem < mDaysPagerAdapter.getCount()) {
            mDaysViewPager.setCurrentItem(currentItem, false);
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState == null) {
            return;
        }

        outState.putSerializable(STATE_HISTORY_MODEL, mHistoryModel);
        outState.putInt(STATE_CURRENT_ITEM, mDaysViewPager.getCurrentItem());
    }

    //*****************************************************************************************************************
    // Net.

    private HistoryModel mHistoryModel;

    /**
     * Gets history and sets data to {@link DaysPagerAdapter}.
     */
    private void netGetHistory() {
        String url = "http://gank.io/api/day/history";
        netGet(url, new NetCallback<HistoryModel>() {
            @Override
            public void onSuccess(@NonNull HistoryModel historyModel) {
                super.onSuccess(historyModel);

                mHistoryModel = historyModel;
                mDaysPagerAdapter.setHistoryModel(mHistoryModel);

                int item = mDaysPagerAdapter.getCount() - 1;
                if (item >= 0 && item < mDaysPagerAdapter.getCount()) {
                    mDaysViewPager.setCurrentItem(item, false);
                }
            }
        });
    }
}
