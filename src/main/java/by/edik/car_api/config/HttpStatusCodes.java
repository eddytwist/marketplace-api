package by.edik.car_api.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpStatusCodes {
    public static final int INTERNAL_ERROR = 500;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int CREATED = 201;
}
