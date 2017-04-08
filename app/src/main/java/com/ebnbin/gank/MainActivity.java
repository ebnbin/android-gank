package com.ebnbin.gank;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ebnbin.ebapplication.context.ui.EBActivity;
import com.ebnbin.gank.feature.days.DaysFragment;

public final class MainActivity extends EBActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaysFragment daysFragment = new DaysFragment();
        if (getFragmentHelper().canAdd(daysFragment)) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            getFragmentHelper()
                    .beginTransaction(ft)
                    .add(daysFragment)
                    .endTransaction();
            ft.commit();
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
