package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestUtil {

    private static final Pattern idPattern = Pattern.compile("/([0-9]*)");

    private RestUtil() {}

    public static Long extractIdFromClientsPath(final String pathInfo) {
        if (Util.isNullOrEmpty(pathInfo)) {
            return null;
        }
        final Matcher matcher = idPattern.matcher(pathInfo);
        if (!matcher.find()) {
            return null;
        }
        final String id = matcher.group(1);
        if (Util.isNullOrEmpty(id)) {
            return null;
        }
        return Long.valueOf(id);
    }
}