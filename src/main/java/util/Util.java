package util;

public class Util {

    private Util() {}

    public static boolean isNullOrEmpty(final String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean exceedsMaxLength(final String value, final int maxLength) {
        return value != null && value.length() > maxLength;
    }
}