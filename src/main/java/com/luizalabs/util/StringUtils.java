package com.luizalabs.util;

public class StringUtils {

    private StringUtils() {}

    public static boolean isNullOrEmpty(final String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean exceedsMaxLength(final String value, final int maxLength) {
        return value != null && value.length() > maxLength;
    }
}