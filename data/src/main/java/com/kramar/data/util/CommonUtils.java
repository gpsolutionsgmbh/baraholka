package com.kramar.data.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CommonUtils {

    public static final ZoneOffset UTC_OFFSET = ZoneOffset.UTC;

    public static LocalDateTime nowUtc() {
        return LocalDateTime.ofInstant(ZonedDateTime.now().toInstant(), UTC_OFFSET);
    }
}
