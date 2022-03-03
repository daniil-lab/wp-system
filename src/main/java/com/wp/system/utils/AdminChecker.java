package com.wp.system.utils;

import com.wp.system.config.security.UserAuthDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AdminChecker {

    public AdminChecker() {}

    public boolean authUserIsAdmin() {
        UserAuthDetails details = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(details == null)
            return false;

        return details.isAdmin();
    }
}
