package com.ebnbin.gank.feature.day;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebnbin.eb.base.EBFragment;
import com.ebnbin.eb.net.NetCallback;
import com.ebnbin.gank.R;

/**
 * Day fragment.
 */
public final class DayFragment extends EBFragment {
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

        if (!onRestoreDay(savedInstanceState)) {
            netGetDay();
        }
    }

    //*****************************************************************************************************************
    // Instance state.

    private static final String KEY_DAY = "day";

    /**
     * 恢复 mDay.
     *
     * @return 如果成功恢复则返回 {@code null}.
     */
    private boolean onRestoreDay(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return false;
        }

        mDay = (Day) savedInstanceState.getSerializable(KEY_DAY);
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

        outState.putSerializable(KEY_DAY, mDay);
    }

    //*****************************************************************************************************************
    // Net.

    private Day mDay;

    /**
     * Gets {@link Day} model and sets data.
     */
    private void netGetDay() {
        String url = "http://gank.io/api/day/2017/03/13";
        netGet(url, new NetCallback<Day>() {
            @Override
            public void onSuccess(@NonNull Day day) {
                super.onSuccess(day);

                mDay = day;
                mDayAdapter.setDay(mDay);
            }
        });
    }
}
