package com.ebnbin.gank.feature.day;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebnbin.eb.util.Date;
import com.ebnbin.ebapplication.base.EBFragment;
import com.ebnbin.ebapplication.net.NetCallback;
import com.ebnbin.gank.R;

import java.util.Locale;

/**
 * 用 {@link RecyclerView} 展示某日期的数据.
 */
public final class DayFragment extends EBFragment {
    //*****************************************************************************************************************
    // Arguments.

    private static final String ARG_DATE = "date";

    /**
     * @param date
     *         日期.
     */
    @NonNull
    public static DayFragment newInstance(@NonNull Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(args);

        return dayFragment;
    }

    /**
     * Date.
     */
    private Date mDate;

    @Override
    protected void onInitArguments(@NonNull Bundle args) {
        super.onInitArguments(args);

        mDate = (Date) args.getSerializable(ARG_DATE);
    }

    //*****************************************************************************************************************

    private RecyclerView mDayRecyclerView;

    @Override
    protected int overrideContentViewLayout() {
        return R.layout.day_fragment;
    }

    @Override
    protected void onInitContentView(@NonNull View contentView) {
        super.onInitContentView(contentView);

        mDayRecyclerView = (RecyclerView) contentView.findViewById(R.id.day);
    }

    private DayLayoutManager mLayoutManager;
    private DayAdapter mAdapter;
    private DayItemDecoration mItemDecoration;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new DayLayoutManager(getContext());
        mDayRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DayAdapter();
        mAdapter.listeners.add(new DayAdapter.Listener() {
            @Override
            void onDataClick(@NonNull DayEntity.Data data) {
                super.onDataClick(data);

                webViewLoadUrl(data.url);
            }
        });
        mDayRecyclerView.setAdapter(mAdapter);

        mItemDecoration = new DayItemDecoration(getContext());
        mDayRecyclerView.addItemDecoration(mItemDecoration);
    }

    //*****************************************************************************************************************
    // Instance state.

    private static final String STATE_DAY_MODEL = "day_model";

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (mDate == null) {
            // TODO: Sets empty view.
            return;
        }

        if (!restoreDay(savedInstanceState)) {
            netGetDay();
        }
    }

    /**
     * 恢复 {@link #mDayModel}.
     *
     * @return 是否成功恢复.
     */
    private boolean restoreDay(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }

        mDayModel = (DayModel) savedInstanceState.getSerializable(STATE_DAY_MODEL);
        if (mDayModel == null) {
            return false;
        }

        mAdapter.setDay(mDayModel);

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState == null) {
            return;
        }

        outState.putSerializable(STATE_DAY_MODEL, mDayModel);
    }

    //*****************************************************************************************************************
    // Net.

    private DayModel mDayModel;

    /**
     * Gets {@link DayModel} model and sets data.
     */
    private void netGetDay() {
        String url = getDayUrl();
        netGet(url, new NetCallback<DayModel>() {
            @Override
            public void onSuccess(@NonNull DayModel dayModel) {
                super.onSuccess(dayModel);

                mDayModel = dayModel;
                mAdapter.setDay(mDayModel);
            }
        });
    }

    /**
     * Gets url of getting {@link DayModel}.
     */
    private String getDayUrl() {
        assert mDate != null;

        String dayFormat = "http://gank.io/api/day/%04d/%02d/%02d";
        return String.format(Locale.getDefault(), dayFormat, mDate.year, mDate.month, mDate.day);
    }
}
