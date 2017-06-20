package com.ebnbin.gank.feature.days;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ebnbin.ebapplication.context.EBActionBarFragment;
import com.ebnbin.ebapplication.feature.about.AboutDialogFragment;
import com.ebnbin.gank.R;
import com.ebnbin.gank.feature.days.dayviewpager.DayViewPagerFragment;

public final class DaysFragment extends EBActionBarFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DayViewPagerFragment dayViewPagerFragment = new DayViewPagerFragment();
        getFragmentHelper().set(dayViewPagerFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        setHasOptionsMenu(true);
    }

    @Override
    protected boolean overrideHasOptionsMenu() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_days, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about: {
                AboutDialogFragment.Companion.showDialog(getChildFragmentManager());

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
