package com.ebnbin.gank.feature.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ebnbin.eb.base.EBFragment;
import com.google.gson.Gson;

import java.io.IOException;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                handler.post(() -> Toast.makeText(getContext(), day.toString(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}
