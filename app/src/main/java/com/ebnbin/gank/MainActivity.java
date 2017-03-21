package com.ebnbin.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.ebnbin.eb.base.EBActivity;
import com.ebnbin.gank.feature.days.DaysFragment;

public final class MainActivity extends EBActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DaysFragment daysFragment = (DaysFragment) fragmentManager.findFragmentByTag(DaysFragment.TAG);
        if (daysFragment == null) {
            daysFragment = new DaysFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, daysFragment, DaysFragment.TAG).commit();
        }
    }
}
