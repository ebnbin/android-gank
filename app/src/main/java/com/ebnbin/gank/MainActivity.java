package com.ebnbin.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ebnbin.ebapplication.base.EBActivity;
import com.ebnbin.gank.feature.days.DaysFragment;

public final class MainActivity extends EBActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DaysFragment daysFragment = (DaysFragment) fragmentManager.findFragmentByTag(DaysFragment.class.getName());
        if (daysFragment == null) {
            daysFragment = new DaysFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, daysFragment, DaysFragment.class.getName()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about: {
                Toast.makeText(getContext(), R.string.main_about_content, Toast.LENGTH_LONG).show();

                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
