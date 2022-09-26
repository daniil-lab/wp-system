package com.wp.system.utils.sber;

import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class SberDateConverter {
    public static Instant getInstantByString(String source) throws ServiceException {
        try {


            Instant date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").parse(source.replace("T", " ")).toInstant();

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("Can`t convert String to Date", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String getStringByInstant(Instant instant) {
        return new SimpleDateFormat("dd.MM.yyyy").format(Date.from(instant));
    }
}
