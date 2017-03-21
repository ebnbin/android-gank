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
    /**
     * Tag for finding fragment.
     */
    public static final String TAG = DayFragment.class.getName();

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

        netGetDay();
    }

    /**
     * Gets {@link Day} model and sets data.
     */
    private void netGetDay() {
        String url = "http://gank.io/api/day/2015/05/18";
        netGet(url, new NetCallback<Day>() {
            @Override
            public void onSuccess(@NonNull Day day) {
                super.onSuccess(day);

                mDayAdapter.setDay(day);
            }
        });
    }
}
