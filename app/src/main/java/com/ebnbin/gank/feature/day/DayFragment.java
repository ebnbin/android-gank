package com.ebnbin.gank.feature.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebnbin.eb.base.EBFragment;
import com.ebnbin.gank.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        getDay();
    }

    /**
     * Gets {@link Day} model and sets data.
     */
    private void getDay() {
        String url = "http://gank.io/api/day/2015/05/18";
        Request request = new Request.Builder().url(url).build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();

                Gson gson = new Gson();
                Day day = gson.fromJson(responseString, Day.class);
                List<DayEntity> dayEntities = DayEntity.newDayEntities(day);

                handler.post(() -> mDayAdapter.setNewData(dayEntities));
            }
        });
    }
}
