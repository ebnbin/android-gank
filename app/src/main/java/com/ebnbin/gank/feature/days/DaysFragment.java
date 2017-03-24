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

        netGetHistory();
    }

    /**
     * Gets history and sets data to {@link DaysPagerAdapter}.
     */
    private void netGetHistory() {
        String url = "http://gank.io/api/day/history";
        netGet(url, new NetCallback<History>() {
            @Override
            public void onSuccess(@NonNull History history) {
                super.onSuccess(history);

                List<int[]> daysHistoryList = DaysUtil.getDaysHistoryList(history);
                mDaysPagerAdapter.setDaysHistoryList(daysHistoryList);

                int count = mDaysPagerAdapter.getCount();
                if (count > 0) {
                    mDaysViewPager.setCurrentItem(count - 1, false);
                }
            }
        });
    }
}
