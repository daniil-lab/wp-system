package com.wp.system.utils;

import com.wp.system.entity.PublicData;
import com.wp.system.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PublicDataSingleton {
    private PublicData data;

    public PublicDataSingleton() {
        if(!new File("publicData.dat").exists()) {
            PublicDataCreator.createPublicData();
        }

        init();
    }

    public void init() {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream("publicData.dat"))) {
            this.data = (PublicData) stream.readObject();
        } catch (Exception ex) {
            throw new ServiceException("Can`t read Public Data from dat file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void save() {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("publicData.dat"))) {
            stream.writeObject(data);
        } catch (Exception ex) {
            throw new ServiceException("Error on create save data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PublicData getData() {
        return data;
    }

    public void setData(PublicData data) {
        this.data = data;
    }
}
