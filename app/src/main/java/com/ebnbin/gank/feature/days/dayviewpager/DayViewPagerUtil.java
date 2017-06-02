package com.ebnbin.gank.feature.days.dayviewpager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ebnbin.eb.util.Timestamp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility class.
 */
final class DayViewPagerUtil {
    /**
     * 根据 {@link HistoryModel} 返回一个用于 {@link DayViewPagerPagerAdapter} 的 {@link Timestamp} {@link ArrayList}.
     *
     * @return 从小到大排序的日期 {@link ArrayList}.
     */
    @NonNull
    public static ArrayList<Timestamp> getDaysHistoryList(@NonNull HistoryModel historyModel) {
        ArrayList<Timestamp> historyList = new ArrayList<>();

        String[] results = historyModel.getResults();
        Arrays.sort(results, String::compareTo);

        for (String result : results) {
            Timestamp timestamp = getTimestamp(result);
            if (timestamp == null) {
                continue;
            }

            historyList.add(timestamp);
        }

        return historyList;
    }

    /**
     * 根据日期字符串解析日期.
     *
     * @param dateString 日期字符串, 1970-01-01 格式.
     *
     * @return 如果数据异常则返回 {@code null}, 否则返回日期.
     */
    @Nullable
    private static Timestamp getTimestamp(@Nullable String dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            return Timestamp.Companion.newInstance(dateString, "yyyy-MM-dd", true);
        } catch (ParseException e) {
            return null;
        }
    }
}
