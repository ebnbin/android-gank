package com.ebnbin.gank.feature.days.day;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebnbin.eb.util.EBUtil;
import com.ebnbin.eb.util.Timestamp;
import com.ebnbin.ebapplication.context.EBActionBarFragment;
import com.ebnbin.ebapplication.context.EBFragment;
import com.ebnbin.ebapplication.net.NetModelCallback;
import com.ebnbin.gank.R;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用 {@link RecyclerView} 展示某日期的数据.
 */
public final class DayFragment extends EBFragment {
    //*****************************************************************************************************************
    // Arguments.

    private static final String ARG_TIMESTAMP = "timestamp";

    @NonNull
    public static DayFragment newInstance(@NonNull Timestamp timestamp) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TIMESTAMP, timestamp);

        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(args);

        return dayFragment;
    }

    private Timestamp mTimestamp;

    @Override
    protected void onInitArguments(@NonNull Bundle args) {
        super.onInitArguments(args);

        mTimestamp = args.getParcelable(ARG_TIMESTAMP);
    }

    //*****************************************************************************************************************

    private RecyclerView mDayRecyclerView;

    @Override
    protected int overrideContentViewLayout() {
        return R.layout.days_day_fragment;
    }

    private DayLayoutManager mLayoutManager;
    private DayAdapter mAdapter;
    private DayItemDecoration mItemDecoration;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDayRecyclerView = view.findViewById(R.id.day);

        mLayoutManager = new DayLayoutManager(getContext());
        mDayRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DayAdapter();
        mAdapter.listeners.add(new DayAdapter.Listener() {
            @Override
            void onDataClick(@NonNull DayEntity.Data data) {
                super.onDataClick(data);

                if (data.dataModel.getValidUrl() != null) {
                    getEbActivity().loadUrl(data.dataModel.getValidUrl());
                } else {
                    // TODO
                }
            }
        });
        mDayRecyclerView.setAdapter(mAdapter);

        mItemDecoration = new DayItemDecoration(getContext());
        mDayRecyclerView.addItemDecoration(mItemDecoration);

        mDayRecyclerView.setItemViewCacheSize(32);
    }

    public RecyclerView getDayRecyclerView() {
        return mDayRecyclerView;
    }

    //*****************************************************************************************************************
    // Instance state.

    private static final String STATE_DAY_MODEL = "day_model";

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (mTimestamp == null) {
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

    public void getDayModel(@NonNull DayModelCallback callback) {
        if (mDayModel == null) {
            mDayModelCallbackArrayList.add(callback);
        } else {
            callback.onGetDayModel(mDayModel);
        }
    }

    private final ArrayList<DayModelCallback> mDayModelCallbackArrayList = new ArrayList<>();

    public static abstract class DayModelCallback {
        public void onGetDayModel(@NonNull DayModel dayModel) {
        }
    }

    /**
     * Gets {@link DayModel} model and sets data.
     */
    private void netGetDay() {
        String url = getDayUrl();
        netGet(url, new NetModelCallback<DayModel>() {
            @Override
            public void onSuccess(@NonNull Call call, @NonNull DayModel model, @NonNull Response response,
                    @NonNull byte[] byteArray) {
                super.onSuccess(call, model, response, byteArray);

                if (getUserVisibleHint()) {
                    EBActionBarFragment actionBarFragment = getActionBarParentFragment();
                    if (actionBarFragment != null) {
                        actionBarFragment.setActionBarMode(EBActionBarFragment.ActionBarMode.SCROLL, true, true, true);
                    }
                }

                mDayModel = model;

                // TODO java.util.ConcurrentModificationException
                for (DayModelCallback dayModelCallback : mDayModelCallbackArrayList) {
                    dayModelCallback.onGetDayModel(mDayModel);

                    mDayModelCallbackArrayList.remove(dayModelCallback);
                }

                mAdapter.setDay(mDayModel);
//
//                // Preload 福利 image.
//                if (!TextUtils.isEmpty(mDayModel.getResults().getValidFuliUrl())) {
//                    Picasso
//                            .with(getContext())
//                            .load(mDayModel.getResults().getValidFuliUrl())
//                            .fetch();
//                }
            }
        });
    }

    @CallSuper
    @Override
    protected void onFront() {
        super.onFront();

        EBUtil.INSTANCE.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mDayRecyclerView == null) {
                    EBUtil.INSTANCE.getHandler().postDelayed(this, 16L);

                    return;
                }

                EBActionBarFragment actionBarFragment = getActionBarParentFragment();
                if (actionBarFragment != null) {
                    actionBarFragment.setNestedScrollingChild(mDayRecyclerView);
                    actionBarFragment.setActionBarMode(EBActionBarFragment.ActionBarMode.SCROLL, false, null, false);
                }
            }
        });
    }

    /**
     * Gets url of getting {@link DayModel}.
     */
    private String getDayUrl() {
        assert mTimestamp != null;

        String dayFormat = "http://gank.io/api/day/%04d/%02d/%02d";
        return String.format(Locale.getDefault(), dayFormat, mTimestamp.getYear(), mTimestamp.getMonth(),
                mTimestamp.getDay());
    }
}
