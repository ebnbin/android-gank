package com.ebnbin.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.ebnbin.ebapplication.context.ui.EBActivity;
import com.ebnbin.gank.feature.days.DaysFragment;

public final class MainActivity extends EBActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaysFragment daysFragment = new DaysFragment();
        if (getFragmentHelper().canAdd(daysFragment)) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            getFragmentHelper()
                    .beginTransaction(ft)
                    .add(daysFragment)
                    .endTransaction();
            ft.commit();
        }
    }
}
