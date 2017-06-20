package com.ebnbin.gank.feature.days.dayviewpager;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.ebnbin.eb.util.EBUtil;
import com.ebnbin.eb.util.Timestamp;
import com.ebnbin.ebapplication.context.EBActionBarFragment;
import com.ebnbin.ebapplication.context.EBFragment;
import com.ebnbin.ebapplication.net.NetModelCallback;
import com.ebnbin.gank.R;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用 {@link ViewPager} 展示多个 {@link com.ebnbin.gank.feature.days.day.DayFragment}.
 */
public final class DayViewPagerFragment extends EBFragment {
    private ViewPager mDaysViewPager;

    @Override
    protected int overrideContentViewLayout() {
        return R.layout.days_day_view_pager_fragment;
    }

    private DayViewPagerPagerAdapter mDayViewPagerPagerAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDaysViewPager = view.findViewById(R.id.days);

        mDayViewPagerPagerAdapter = new DayViewPagerPagerAdapter(getChildFragmentManager());
        mDaysViewPager.setAdapter(mDayViewPagerPagerAdapter);

        mDaysViewPager.setOffscreenPageLimit(1);

        int marginPixels = getResources().getDimensionPixelSize(R.dimen.days_page_margin);
        mDaysViewPager.setPageMargin(marginPixels);

        Drawable d = new ColorDrawable(EBUtil.INSTANCE.getColorAttr(getContext(), R.attr.ebColorPlaceholder));
        mDaysViewPager.setPageMarginDrawable(d);

        mDaysViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private boolean mDragged = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTitle(position);

                EBActionBarFragment actionBarFragment = getActionBarParentFragment();
                if (actionBarFragment != null && mDragged) {
                    mDragged = false;

                    actionBarFragment.getAppBarLayout().setExpanded(true, true);
                }

                mDayViewPagerPagerAdapter.getFuliUrl(position, new DayViewPagerPagerAdapter.FuliUrlCallback() {
                    @Override
                    public void onGetFuliUrl(@Nullable String fuliUrl) {
                        super.onGetFuliUrl(fuliUrl);

                        // TODO: 福利 image.
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    mDragged = true;
                }
            }
        });
    }

    /**
     * Sets title of {@link ActionBar}.
     */
    private void setTitle(int position) {
        ActionBar actionBar = getEbActivity().getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        Timestamp timestamp = mDayViewPagerPagerAdapter.timestamps.get(position);
        if (timestamp == null) {
            return;
        }

        String title = getString(R.string.days_title, timestamp.getYear(), timestamp.getMonth(), timestamp.getDay());
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

        mDayViewPagerPagerAdapter.setHistoryModel(mHistoryModel);

        int defaultCurrentItem = mDayViewPagerPagerAdapter.getCount() - 1;
        int currentItem = savedInstanceState.getInt(STATE_CURRENT_ITEM, defaultCurrentItem);
        if (currentItem >= 0 && currentItem < mDayViewPagerPagerAdapter.getCount()) {
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
     * Gets history and sets data to {@link DayViewPagerPagerAdapter}.
     */
    private void netGetHistory() {
        String url = "http://gank.io/api/day/history";
        netGet(url, new NetModelCallback<HistoryModel>() {
            @Override
            public void onSuccess(@NonNull Call call, @NonNull HistoryModel model, @NonNull Response response,
                    @NonNull byte[] byteArray) {
                super.onSuccess(call, model, response, byteArray);

                mHistoryModel = model;
                mDayViewPagerPagerAdapter.setHistoryModel(mHistoryModel);

                int item = mDayViewPagerPagerAdapter.getCount() - 1;
                if (item >= 0 && item < mDayViewPagerPagerAdapter.getCount()) {
                    mDaysViewPager.setCurrentItem(item, false);
                }
            }
        });
    }
}
