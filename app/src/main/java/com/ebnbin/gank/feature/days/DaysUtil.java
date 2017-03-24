package com.ebnbin.gank.feature.days;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class.
 */
final class DaysUtil {
    /**
     * 日期字符串分隔符.
     */
    private static final String DATE_SPLIT = "-";

    /**
     * 根据 {@link History} 返回一个用于 {@link DaysPagerAdapter} 的日期 {@link List}.
     *
     * @param history
     *         {@link History} model.
     *
     * @return 从小到大排序的日期 {@link List}.
     */
    @NonNull
    public static List<int[]> getDaysHistoryList(@NonNull History history) {
        List<int[]> historyList = new ArrayList<>();

        String[] results = history.getResults();
        Arrays.sort(results, String::compareTo);

        for (String result : results) {
            int[] date = getDate(result);
            if (date == null) {
                continue;
            }

            historyList.add(date);
        }

        return historyList;
    }

    /**
     * 根据日期字符串解析日期数组.
     * TODO: {@link DateFormat}.
     *
     * @param dateString
     *         日期字符串, 1970-01-01 格式.
     *
     * @return 如果数据异常则返回 {@code null}, 否则返回日期数据.
     */
    @Nullable
    private static int[] getDate(@Nullable String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return null;
        }

        String[] dateStringSplit = dateString.split(DATE_SPLIT);
        if (dateStringSplit.length != 3) {
            return null;
        }

        int[] date = new int[3];
        try {
            date[0] = Integer.parseInt(dateStringSplit[0]);
            date[1] = Integer.parseInt(dateStringSplit[1]);
            date[2] = Integer.parseInt(dateStringSplit[2]);
        } catch (NumberFormatException e) {
            return null;
        }

        // TODO: Checks the validity of date.

        return date;
    }
}
