package com.treela.thefarmerguy.tools;

import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager {
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private UserRepository userRepo;
    
    public boolean isAdmin(String cookie_session_id, String cookie_session_userid, String param_session_id, String param_session_userid) {
        if (cookie_session_id.equals("null") || cookie_session_id.equals("")) {
            cookie_session_id = param_session_id;
            cookie_session_userid = param_session_userid;
        }
        //System.out.println(cookie_session_id + cookie_session_userid);
        try {
            System.out.println("***************-admin-" + sessionRepo.isAdmin(cookie_session_id, Long.parseLong(cookie_session_userid)).size());
            return sessionRepo.isAdmin(cookie_session_id, Long.parseLong(cookie_session_userid)).size() > 0;
        } catch(Exception e) {
            System.out.println("*****************************isAdmin-" + e);
            return false;
        }
    }
    
    public boolean checkLogin(String cookie_session_id, String cookie_session_userid, String param_session_id, String param_session_userid) {
        if (cookie_session_id.equals("null") || cookie_session_id.equals("")) {
            cookie_session_id = param_session_id;
            cookie_session_userid = param_session_userid;
        }
        //System.out.println(cookie_session_id + cookie_session_userid);
        try {
            System.out.println("***************-check login-");
            return sessionRepo.existsByIdAndUserid(cookie_session_id, Long.parseLong(cookie_session_userid));
        } catch(Exception e) {
            System.out.println("*****************************check login-" + e);
            return false;
        }
    }
}