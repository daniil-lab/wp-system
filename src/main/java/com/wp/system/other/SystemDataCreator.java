package com.wp.system.other;

import com.wp.system.entity.SystemData;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SystemDataCreator {
    public static void createSystemData() {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("systemData.dat"))) {
            stream.writeObject(new SystemData());
        } catch (Exception ex) {
            throw new ServiceException("Error on create system data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
