package com.ebnbin.gank.feature.days;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ebnbin.ebapplication.context.ui.EBActionBarFragment;
import com.ebnbin.ebapplication.fragment.about.AboutDialogFragment;
import com.ebnbin.gank.R;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        setHasOptionsMenu(true);
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
                AboutDialogFragment.showDialog(getChildFragmentManager());

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
