package com.wp.system.other;

import com.wp.system.exception.ServiceException;
import com.wp.system.exception.system.SystemErrorCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class SystemDateConverter {
    public static Instant getInstantByString(String source) throws ServiceException {
        try {
            Instant date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(source).toInstant();

            return date;
        } catch (ParseException e) {
            throw new ServiceException(SystemErrorCode.INTERNAL_ERROR);
        }
    }

    public static String getStringByInstant(Instant instant) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date.from(instant));
    }
}
