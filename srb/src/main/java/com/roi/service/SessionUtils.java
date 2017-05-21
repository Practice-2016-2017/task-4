package com.roi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SessionUtils {
    @Autowired
    private SessionRegistry sessionRegistry;

    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                }
        }
    }
}