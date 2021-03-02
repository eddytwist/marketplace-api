package by.edik.car_api.web.utils;

public final class UriUtils {
    public static Long getId(String uri) {
        return Long.parseLong(uri.replace("/", ""));
    }
}
