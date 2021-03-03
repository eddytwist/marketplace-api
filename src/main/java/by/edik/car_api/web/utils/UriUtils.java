package by.edik.car_api.web.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriUtils {
    private static final Pattern COMPILE = Pattern.compile("/", Pattern.LITERAL);

    public static Long getId(String uri) {
        return Long.parseLong(COMPILE.matcher(uri).replaceAll(Matcher.quoteReplacement("")));
    }
}
