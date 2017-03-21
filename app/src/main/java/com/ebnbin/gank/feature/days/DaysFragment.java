package com.ebnbin.gank.feature.days;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebnbin.ebapplication.base.EBFragment;
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
    }
}
