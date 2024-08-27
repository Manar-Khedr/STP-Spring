package com.sumerge.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    public boolean isAdmin() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        String adminToken = request.getHeader("Admin-Token");
        if (adminToken == null) {
            adminToken = request.getParameter("Admin-Token");
        }

        System.out.println("Admin-Token: " + adminToken);  // Debug log

        return "Admin".equals(adminToken);
    }


}