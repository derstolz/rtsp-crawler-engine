package com.storage.cameras.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@NoArgsConstructor(access = PRIVATE)
public final class DateTimeUtil {

    private final static String DEFAULT_DATETIME_FORMAT = "yyyy-mm-dd hh:mm:ss";
    private final static DateFormat DEFAULT_FORMATTER = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

    public static String formatDateToString(final Date date) {
        if (date != null) {
            return DEFAULT_FORMATTER.format(date);
        }
        return null;
    }

    public static Date formatStringWithDate(final String date) {
        if (isNotBlank(date)) {
            try {
                return DEFAULT_FORMATTER.parse(date);
            } catch (final ParseException e) {
                return null;
            }
        }
        return null;
    }
}
