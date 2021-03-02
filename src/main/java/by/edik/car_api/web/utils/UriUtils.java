package by.edik.car_api.web.utils;

final public class UriUtils {
    public static Long getId(String uri) {
        return Long.parseLong(uri.replace("/",""));
    }
}
