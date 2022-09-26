package com.wp.system.services.system;

import com.wp.system.entity.PublicData;
import com.wp.system.request.system.UpdatePublicDataRequest;
import com.wp.system.utils.PublicDataSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicDataService {
    @Autowired
    private PublicDataSingleton publicDataSingleton;

    public PublicData getPublicData() {
        return publicDataSingleton.getData();
    }

    public Boolean updatePublicData(UpdatePublicDataRequest request) {
        PublicData data = publicDataSingleton.getData();

        if(request.getEmails() != null) {
            List<String> emails = List.of(request.getEmails().split(","));

            data.setEmails(emails);
        }

        if(request.getConfPolicy() != null)
            data.setConfPolicy(request.getConfPolicy());

        if(request.getFacebook() != null)
            data.setFacebook(request.getFacebook());

        if(request.getInstagram() != null)
            data.setInstagram(request.getInstagram());

        if(request.getPhones() != null) {
            List<String> phones = List.of(request.getPhones().split(","));

            data.setPhoneNumbers(phones);
        }

        if(request.getVk() != null)
            data.setVk(request.getVk());

        if(request.getWebsite() != null)
            data.setWebsite(request.getWebsite());

        if(request.getYoutube() != null)
            data.setYoutube(request.getYoutube());

        publicDataSingleton.save();

        return true;
    }
}
