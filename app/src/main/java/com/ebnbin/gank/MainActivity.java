package com.ebnbin.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.ebnbin.eb.base.EBActivity;
import com.ebnbin.gank.feature.day.DayFragment;

public final class MainActivity extends EBActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DayFragment dayFragment = (DayFragment) fragmentManager.findFragmentByTag(DayFragment.TAG);
        if (dayFragment == null) {
            dayFragment = new DayFragment();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, dayFragment, DayFragment.TAG)
                    .commit();
        }
    }
}
