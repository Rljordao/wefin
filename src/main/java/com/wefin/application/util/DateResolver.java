package com.wefin.application.util;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateResolver {

    private static final ZoneId ZONE_SAO_PAULO = ZoneId.of("America/Sao_Paulo");

    public static Date dateNow() {
        return Date.from(ZonedDateTime.now(ZONE_SAO_PAULO).toInstant());
    }

    public static LocalDateTime localDateTimeNow() {
        return LocalDateTime.now(ZONE_SAO_PAULO);
    }
}
