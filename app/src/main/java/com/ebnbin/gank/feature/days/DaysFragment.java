package com.ebnbin.gank.feature.days;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ebnbin.ebapplication.context.ui.EBActionBarFragment;
import com.ebnbin.gank.feature.daysviewpager.DaysViewPagerFragment;

public final class DaysFragment extends EBActionBarFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DaysViewPagerFragment daysViewPagerFragment = new DaysViewPagerFragment();
        if (getFragmentHelper().canAdd(daysViewPagerFragment)) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            getFragmentHelper()
                    .beginTransaction(ft)
                    .add(COORDINATOR_LAYOUT_CONTENT_CONTAINER_ID, daysViewPagerFragment);
            ft.commit();
        }
    }
}
