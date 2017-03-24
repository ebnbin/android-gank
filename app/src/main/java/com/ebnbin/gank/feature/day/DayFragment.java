package com.ebnbin.gank.feature.day;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebnbin.ebapplication.base.EBFragment;
import com.ebnbin.ebapplication.net.NetCallback;
import com.ebnbin.gank.R;

import java.util.Locale;

/**
 * 用 {@link RecyclerView} 展示某日期的数据.
 */
public final class DayFragment extends EBFragment {
    private static final String ARG_DATE = "date";

    /**
     * @param date
     *         日期.
     */
    @NonNull
    public static DayFragment newInstance(@NonNull int[] date) {
        Bundle args = new Bundle();
        args.putIntArray(ARG_DATE, date);

        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(args);

        return dayFragment;
    }

    /**
     * Date.
     */
    private int[] mDate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // TODO: Moves to library.
        initArguments();
    }

    private void initArguments() {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        mDate = args.getIntArray(ARG_DATE);
    }

    //*****************************************************************************************************************

    private RecyclerView mDayRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.day_fragment, container, false);

        mDayRecyclerView = (RecyclerView) rootView.findViewById(R.id.day);

        return rootView;
    }

    private LinearLayoutManager mDayLayoutManager;
    private DayAdapter mDayAdapter;
    private DayItemDecoration mDayItemDecoration;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDayLayoutManager = new LinearLayoutManager(getContext());
        mDayRecyclerView.setLayoutManager(mDayLayoutManager);

        mDayAdapter = new DayAdapter();
        mDayRecyclerView.setAdapter(mDayAdapter);

        mDayItemDecoration = new DayItemDecoration(getContext());
        mDayRecyclerView.addItemDecoration(mDayItemDecoration);

        if (mDate == null) {
            // TODO: Sets empty view.
            return;
        }

        if (!onRestoreDay(savedInstanceState)) {
            netGetDay();
        }
    }

    //*****************************************************************************************************************
    // Instance state.

    private static final String STATE_DAY = "day";

    /**
     * 恢复 mDay.
     *
     * @return 如果成功恢复则返回 {@code null}.
     */
    private boolean onRestoreDay(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }

        mDay = (Day) savedInstanceState.getSerializable(STATE_DAY);
        if (mDay == null) {
            return false;
        }

        mDayAdapter.setDay(mDay);

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState == null) {
            return;
        }

        outState.putSerializable(STATE_DAY, mDay);
    }

    //*****************************************************************************************************************
    // Net.

    private Day mDay;

    /**
     * Gets {@link Day} model and sets data.
     */
    private void netGetDay() {
        String url = getDayUrl();
        netGet(url, new NetCallback<Day>() {
            @Override
            public void onSuccess(@NonNull Day day) {
                super.onSuccess(day);

                mDay = day;
                mDayAdapter.setDay(mDay);
            }
        });
    }

    /**
     * Gets url of getting {@link Day}.
     */
    private String getDayUrl() {
        assert mDate != null;

        String dayFormat = "http://gank.io/api/day/%04d/%02d/%02d";
        return String.format(Locale.getDefault(), dayFormat, mDate[0], mDate[1], mDate[2]);
    }
}
