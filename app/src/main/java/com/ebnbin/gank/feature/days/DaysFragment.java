package com.ebnbin.gank.feature.days;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebnbin.ebapplication.base.EBFragment;
import com.ebnbin.ebapplication.net.NetCallback;
import com.ebnbin.gank.R;
import com.ebnbin.gank.feature.day.DayFragment;

import java.util.List;

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

        mDaysViewPager.setOffscreenPageLimit(3);

        if (!restoreHistory(savedInstanceState)) {
            netGetHistory();
        }
    }

    //*****************************************************************************************************************
    // Instance state.

    private static final String STATE_HISTORY = "history";
    private static final String STATE_CURRENT_ITEM = "current_item";

    /**
     * 恢复 {@link #mHistory}.
     *
     * @return 是否成功恢复.
     */
    public boolean restoreHistory(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }

        mHistory = (History) savedInstanceState.getSerializable(STATE_HISTORY);
        if (mHistory == null) {
            return false;
        }

        List<int[]> daysHistoryList = DaysUtil.getDaysHistoryList(mHistory);
        mDaysPagerAdapter.setDaysHistoryList(daysHistoryList);

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

        outState.putSerializable(STATE_HISTORY, mHistory);
        outState.putInt(STATE_CURRENT_ITEM, mDaysViewPager.getCurrentItem());
    }

    //*****************************************************************************************************************
    // Net.

    private History mHistory;

    /**
     * Gets history and sets data to {@link DaysPagerAdapter}.
     */
    private void netGetHistory() {
        String url = "http://gank.io/api/day/history";
        netGet(url, new NetCallback<History>() {
            @Override
            public void onSuccess(@NonNull History history) {
                super.onSuccess(history);

                mHistory = history;

                List<int[]> daysHistoryList = DaysUtil.getDaysHistoryList(mHistory);
                mDaysPagerAdapter.setDaysHistoryList(daysHistoryList);

                int item = mDaysPagerAdapter.getCount() - 1;
                if (item >= 0 && item < mDaysPagerAdapter.getCount()) {
                    mDaysViewPager.setCurrentItem(item, false);
                }
            }
        });
    }
}
