package by.edik.car_api.web.utils;

import by.edik.car_api.web.exception.BadRequestParamsException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriUtils {
    private static final Pattern COMPILE = Pattern.compile("/", Pattern.LITERAL);

    public static Long getId(String uri) {
        String sId = COMPILE.matcher(uri).replaceAll(Matcher.quoteReplacement(""));

        try {
            return Long.parseLong(sId);
        } catch (NumberFormatException e) {
            throw new BadRequestParamsException("Id should be number, but got: " + sId, e);
        }
    }
}
