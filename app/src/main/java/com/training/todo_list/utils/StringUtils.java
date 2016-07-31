package com.training.todo_list.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringUtils {

    public static boolean isNullOrEmpty(String pString) {
        return null == pString || "".equals(pString);
    }

    public static boolean areEquals(@Nullable String pSFirst, @Nullable String pSSecond) {
        if (null == pSFirst)
            return null == pSSecond;
        return null != pSSecond && pSFirst.equals(pSSecond);
    }


    private WeakReference<Resources> mRes;


    public StringUtils(Resources pRes) {
        mRes = new WeakReference<>(pRes);
    }


    /**
     * @param pIResId : must be a valid id
     * @return the string corresponding to given Id.
     */
    public String stringFor(int pIResId) {
        Resources tRes = mRes.get();
        if (null != tRes)
            return tRes.getString(pIResId);
        return "";
    }

    /**
     * @return the date formatted in english, short format.
     */
    public String dayFor(@NonNull Date pDate) {
        DateFormat tDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);
        tDateFormat.setTimeZone(TimeZone.getDefault());
        return tDateFormat.format(pDate);
    }

    /**
     * @return the time formatted in english, short format.
     */
    public String timeFor(@NonNull Date pDate) {
        DateFormat tDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH);
        tDateFormat.setTimeZone(TimeZone.getDefault());
        return tDateFormat.format(pDate);
    }

    /**
     * @return a color parsed from the given string. If pSColor is null or
     * does not represent a color, pIDefaultColor is returned;
     */
    public int colorFor(String pSColor, int pIDefaultColor) {
        if (isNullOrEmpty(pSColor))
            return pIDefaultColor;
        try {
            return Color.parseColor(pSColor);
        } catch (IllegalArgumentException e) {
            return pIDefaultColor;
        }
    }
}
