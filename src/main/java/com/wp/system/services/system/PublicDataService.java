package com.wp.system.services.system;

import com.wp.system.entity.PublicData;
import com.wp.system.utils.PublicDataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicDataService {
    @Autowired
    private PublicDataSingleton publicDataSingleton;

    public PublicData getPublicData() {
        return publicDataSingleton.getData();
    }
}
