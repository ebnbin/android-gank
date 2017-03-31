package com.ebnbin.gank.feature.daysviewpager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.ebnbin.eb.base.EBRuntimeException;
import com.ebnbin.eb.util.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class.
 */
final class DaysViewPagerUtil {
    /**
     * 日期字符串分隔符.
     */
    private static final String DATE_SPLIT = "-";

    /**
     * 根据 {@link HistoryModel} 返回一个用于 {@link DaysViewPagerPagerAdapter} 的 {@link Date} {@link List}.
     *
     * @return 从小到大排序的日期 {@link List}.
     */
    @NonNull
    public static List<Date> getDaysHistoryList(@NonNull HistoryModel historyModel) {
        List<Date> historyList = new ArrayList<>();

        String[] results = historyModel.getResults();
        Arrays.sort(results, String::compareTo);

        for (String result : results) {
            Date date = getDate(result);
            if (date == null) {
                continue;
            }

            historyList.add(date);
        }

        return historyList;
    }

    /**
     * 根据日期字符串解析日期.
     * TODO: {@link DateFormat}.
     *
     * @param dateString
     *         日期字符串, 1970-01-01 格式.
     *
     * @return 如果数据异常则返回 {@code null}, 否则返回日期.
     */
    @Nullable
    private static Date getDate(@Nullable String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return null;
        }

        String[] dateStringSplit = dateString.split(DATE_SPLIT);
        if (dateStringSplit.length != 3) {
            return null;
        }

        try {
            int year = Integer.parseInt(dateStringSplit[0]);
            int month = Integer.parseInt(dateStringSplit[1]);
            int day = Integer.parseInt(dateStringSplit[2]);
            return new Date(year, month, day);
        } catch (NumberFormatException | EBRuntimeException e) {
            return null;
        }
    }
}
