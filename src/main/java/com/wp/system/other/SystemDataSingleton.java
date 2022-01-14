package com.wp.system.other;

import com.wp.system.entity.SystemData;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

@Component
public class SystemDataSingleton {
    private SystemData data;

    public SystemDataSingleton() {
        if(!new File("systemData.dat").exists()) {
            SystemDataCreator.createSystemData();
        }

        init();
    }

    public void init() {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream("systemData.dat"))) {
            this.data = (SystemData) stream.readObject();
        } catch (Exception ex) {
            throw new ServiceException("Can`t read System Data from dat file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SystemData getData() {
        return data;
    }

    public void setData(SystemData data) {
        this.data = data;
    }
}
