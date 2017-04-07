package com.ebnbin.gank.feature.daysviewpager;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ebnbin.eb.util.Date;
import com.ebnbin.eb.util.EBUtil;
import com.ebnbin.ebapplication.context.ui.EBFragment;
import com.ebnbin.ebapplication.net.NetModelCallback;
import com.ebnbin.gank.R;

import okhttp3.Call;

/**
 * 用 {@link ViewPager} 展示多个 {@link com.ebnbin.gank.feature.day.DayFragment}.
 */
public final class DaysViewPagerFragment extends EBFragment {
    private ViewPager mDaysViewPager;

    @Override
    protected int overrideContentViewLayout() {
        return R.layout.days_fragment;
    }

    @Override
    protected void onInitContentView(@NonNull View contentView) {
        super.onInitContentView(contentView);

        mDaysViewPager = (ViewPager) contentView.findViewById(R.id.days);
    }

    private DaysViewPagerPagerAdapter mDaysViewPagerPagerAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDaysViewPagerPagerAdapter = new DaysViewPagerPagerAdapter(getChildFragmentManager());
        mDaysViewPager.setAdapter(mDaysViewPagerPagerAdapter);

        mDaysViewPager.setOffscreenPageLimit(3);

        int marginPixels = getResources().getDimensionPixelSize(R.dimen.days_page_margin);
        mDaysViewPager.setPageMargin(marginPixels);

        Drawable d = new ColorDrawable(EBUtil.getColorAttr(getContext(), R.attr.ebColorPlaceholder));
        mDaysViewPager.setPageMarginDrawable(d);

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
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        ActionBar actionBar = activity.getActionBar();
        if (actionBar == null) {
            return;
        }

        Date date = mDaysViewPagerPagerAdapter.dates.get(position);
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

        mDaysViewPagerPagerAdapter.setHistoryModel(mHistoryModel);

        int defaultCurrentItem = mDaysViewPagerPagerAdapter.getCount() - 1;
        int currentItem = savedInstanceState.getInt(STATE_CURRENT_ITEM, defaultCurrentItem);
        if (currentItem >= 0 && currentItem < mDaysViewPagerPagerAdapter.getCount()) {
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
     * Gets history and sets data to {@link DaysViewPagerPagerAdapter}.
     */
    private void netGetHistory() {
        String url = "http://gank.io/api/day/history";
        netGet(url, new NetModelCallback<HistoryModel>() {
            @Override
            public void onSuccess(@NonNull Call call, @NonNull HistoryModel model) {
                super.onSuccess(call, model);

                mHistoryModel = model;
                mDaysViewPagerPagerAdapter.setHistoryModel(mHistoryModel);

                int item = mDaysViewPagerPagerAdapter.getCount() - 1;
                if (item >= 0 && item < mDaysViewPagerPagerAdapter.getCount()) {
                    mDaysViewPager.setCurrentItem(item, false);
                }
            }
        });
    }
}
