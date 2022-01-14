package com.wp.system.services.system;

import com.wp.system.entity.SystemData;
import com.wp.system.other.SystemDataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemDataService {
    @Autowired
    private SystemDataSingleton systemDataSingleton;

    public SystemData getSystemData() {
        return systemDataSingleton.getData();
    }
}
