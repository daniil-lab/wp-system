package com.wp.system.utils;

import com.wp.system.entity.PublicData;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class PublicDataCreator {
    public static void createPublicData() {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("publicData.dat"))) {
            stream.writeObject(new PublicData());
        } catch (Exception ex) {
            throw new ServiceException("Error on create public data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
