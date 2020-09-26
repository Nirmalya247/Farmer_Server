package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.Session;
import com.treela.thefarmerguy.model.User;
import com.treela.thefarmerguy.repository.DemandRepository;
import com.treela.thefarmerguy.repository.OTPRepository;
import com.treela.thefarmerguy.repository.SellrequestRepository;
import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.ShopRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import com.treela.thefarmerguy.tools.OTPManager;
import com.treela.thefarmerguy.tools.UserManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserApi {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private ShopRepository shopRepo;
    @Autowired
    private UserManager userManager;
    @Autowired
    private DemandRepository demandRepo;
    @Autowired
    private SellrequestRepository sellReqRepo;
    @Autowired
    private OTPRepository otpRepo;
    @Autowired
    private OTPManager otpManager;
    @PostMapping(path="/register")
    public Map<String, Object> addNewUser(
            @RequestParam long otp_id,
            @RequestParam String otp_code,
            @RequestParam int otp_otp,
            @RequestParam long phoneno,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam int pincode,
            @RequestParam String area) {
        Map<String, Object> obj = new HashMap<String, Object>();
        if (otpManager.checkOTP(otp_id, otp_code, otp_otp, phoneno)) {
            try {
                User n = new User(phoneno, name, password, pincode, area, 0);
                userRepo.save(n);
                obj.put("err", false);
                obj.put("message", "registered");
            } catch (Exception e) {
                obj.put("err", true);
                obj.put("message", "some error");
            }
        } else {
            obj.put("err", true);
            obj.put("message", "not a valid otp");
        }
        return obj;
    }
    
    // login user
    @PostMapping(path="/login") // Map ONLY POST Requests
    public Map<String, Object> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam long phoneno, @RequestParam String password,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        
        //System.out.println("************************************" + bod);
        //long phoneno = 99;
        //String password = "as";
        
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_id.equals("null") || session_id.equals("")) {
            session_id = param_session_id;
            session_userid = param_session_userid;
        }
        try {
            obj.put("err", false);
            obj.put("msg", "you are logged in");
            
            if (!session_id.equals("null")) {
                Session t = sessionRepo.findById(session_id);
                if (t != null) {
                    sessionRepo.delete(t);
                }
            }
            if (userRepo.existsByPhonenoAndPassword(phoneno, password)) {
                User user = userRepo.findByPhoneno(phoneno);
                Session s = new Session(user.getId(), "unknown", request.getRemoteAddr());
                sessionRepo.save(s);
                Cookie cookie = new Cookie("session_id", s.getId());
                cookie.setPath("/");
                cookie.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(cookie);
                cookie = new Cookie("session_userid", String.valueOf(s.getUserid()));
                cookie.setPath("/");
                cookie.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(cookie);
                obj.put("session_id", s.getId());
                obj.put("session_userid", s.getUserid());
                obj.put("loggedin", true);
                obj.put("id", user.getId());
                obj.put("phoneno", user.getPhoneno());
                obj.put("name", user.getName());
                obj.put("isadmin", user.getIsadmin());
                obj.put("pincode", user.getPincode());
                obj.put("area", user.getArea());
            } else {
                obj.put("err", true);
                obj.put("msg", "wrong ph.no. or password");
            }
        } catch(Exception e) {
            System.out.println("*****************************exception-login-" + e);
            obj.put("err", true);
            obj.put("msg", "unknown error");
            //return "err";
        }
        return obj;
    }

    // check login
    @PostMapping(path="/checklogin") // Map ONLY POST Requests
    public Map<String, Object> checkLogin(
            HttpServletResponse response,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_id.equals("null") || session_id.equals("")) {
            session_id = param_session_id;
            session_userid = param_session_userid;
        }
        try {
            if (!session_id.equals("null") && sessionRepo.existsByIdAndUserid(session_id, Long.parseLong(session_userid))) {
                User user = userRepo.findById(Long.parseLong(session_userid));
                obj.put("err", false);
                obj.put("loggedin", true);
                obj.put("id", user.getId());
                obj.put("phoneno", user.getPhoneno());
                obj.put("name", user.getName());
                obj.put("isadmin", user.getIsadmin());
                obj.put("pincode", user.getPincode());
                obj.put("area", user.getArea());
                obj.put("msg", "you are logged in");
            } else if (!session_id.equals("null")) {
                Session t = sessionRepo.findById(session_id);
                if (t != null) {
                    sessionRepo.delete(t);
                }
                Cookie cookie = new Cookie("session_id", "null");
                cookie.setPath("/");
                cookie.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(cookie);
                cookie = new Cookie("session_userid", "null");
                cookie.setPath("/");
                cookie.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(cookie);
                obj.put("err", false);
                obj.put("loggedin", false);
                obj.put("msg", "you have been logged out");
            } else {
                obj.put("err", false);
                obj.put("loggedin", false);
                obj.put("msg", "you have not logged in");
            }
        } catch (Exception e) {
            System.out.println("*****************************exception-checklogin-" + e);
            obj.put("err", true);
            obj.put("loggedin", false);
            obj.put("msg", "unknown error");
            //return "err";
        }
        return obj;
    }

    // logout
    @PostMapping(path="/logout") // Map ONLY POST Requests
    public Map<String, Object> logout(
            HttpServletResponse response,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_id.equals("null") || session_id.equals("")) {
            session_id = param_session_id;
            session_userid = param_session_userid;
        }
        try {
            if (!session_id.equals("null")) {
                Session t = sessionRepo.findById(session_id);
                if (t != null) {
                    sessionRepo.delete(t);
                }
            }
            Cookie cookie = new Cookie("session_id", "null");
            cookie.setPath("/");
            cookie.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(cookie);
            cookie = new Cookie("session_userid", "null");
            cookie.setPath("/");
            cookie.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(cookie);
            obj.put("err", false);
            obj.put("loggedout", true);
            obj.put("msg", "you have been logged out");
        } catch (Exception e) {
            System.out.println("*****************************exception-logout-" + e);
            obj.put("err", true);
            obj.put("loggedout", true);
            obj.put("msg", "unknown error");
            //return "err";
        }
        return obj;
    }

    // profie edit
    @PostMapping(path="/profileEdit")
    public Map<String, Object> profileEdit(
            HttpServletResponse response,
            @RequestParam(value = "name", defaultValue = "null") String name,
            @RequestParam(value = "pincode", defaultValue = "0") int pincode,
            @RequestParam(value = "area", defaultValue = "null") String area,
            @RequestParam(value = "password", defaultValue = "null") String password,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_userid.equals("null")) {
            session_userid = param_session_userid;
        }
        long userid = Long.parseLong(session_userid);
        if (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid) &&
                userRepo.existsByIdAndPassword(userid, password)) {
            User tUser = userRepo.findById(userid);
            try {
                if (!name.equals("null")) tUser.setName(name);
                if (pincode > 0) tUser.setPincode(pincode);
                if (!area.equals("null")) tUser.setArea(area);
                userRepo.save(tUser);
                obj.put("err", false);
                obj.put("msg", "saved");
                return obj;
            } catch (Exception e) {
                System.out.println("*****************************profile-edit-" + e);
            }
        }
        obj.put("err", true);
        obj.put("msg", "unknown error");
        return obj;
    }

    // profie change password
    @PostMapping(path="/profileChangePassword")
    public Map<String, Object> profileChangePassword(
            HttpServletResponse response,
            @RequestParam String newPassword,
            @RequestParam String oldPassword,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_userid.equals("null")) {
            session_userid = param_session_userid;
        }
        long userid = Long.parseLong(session_userid);
        if (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid) &&
                userRepo.existsByIdAndPassword(userid, oldPassword)) {
            User tUser = userRepo.findById(userid);
            try {
                tUser.setPassword(newPassword);
                userRepo.save(tUser);
                obj.put("err", false);
                obj.put("msg", "saved");
                return obj;
            } catch (Exception e) {
                System.out.println("*****************************profile-change-password-" + e);
            }
        }
        obj.put("err", true);
        obj.put("msg", "unknown error");
        return obj;
    }

    // profie change phoneno
    @PostMapping(path="/profileChangePhoneno")
    public Map<String, Object> profileChangePhoneno(
            HttpServletRequest request,
            @RequestParam long otp_id,
            @RequestParam String otp_code,
            @RequestParam int otp_otp,
            @RequestParam long old_phoneno,
            @RequestParam long new_phoneno,
            @RequestParam String password,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_userid.equals("null")) {
            session_userid = param_session_userid;
        }
        long userid = Long.parseLong(session_userid);
        if (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid) &&
                userRepo.existsByIdAndPassword(userid, password) &&
                otpManager.checkOTP(otp_id, otp_code, otp_otp, new_phoneno)) {
            User tUser = userRepo.findById(userid);
            try {
                tUser.setPhoneno(new_phoneno);
                userRepo.save(tUser);
                obj.put("err", false);
                obj.put("msg", "saved");
                return obj;
            } catch (Exception e) {
                System.out.println("*****************************profile-change-phoneno-" + e);
            }
        }
        obj.put("err", true);
        obj.put("msg", "unknown error");
        return obj;
    }

    // profie forgot password
    @PostMapping(path="/loginForgotPassword")
    public Map<String, Object> loginForgotPassword(
            HttpServletRequest request,
            @RequestParam long otp_id,
            @RequestParam String otp_code,
            @RequestParam int otp_otp,
            @RequestParam long phoneno,
            @RequestParam String password) {
        //JSONObject obj = new JSONObject();
        Map<String, Object> obj = new HashMap<String, Object>();
        if (otpManager.checkOTP(otp_id, otp_code, otp_otp, phoneno)) {
            User tUser = userRepo.findByPhoneno(phoneno);
            try {
                tUser.setPassword(password);
                userRepo.save(tUser);
                obj.put("err", false);
                obj.put("msg", "saved");
                return obj;
            } catch (Exception e) {
                System.out.println("*****************************profile-forgot-password-" + e);
            }
        }
        obj.put("err", true);
        obj.put("msg", "unknown error");
        return obj;
    }
    // get pincode
    @PostMapping(path="/getPincode")
    public List<Map<String, Object>> getPincode(
            @RequestParam int pincode) {
        try {
            return userRepo.getPincode(pincode);
        } catch (Exception e) { }
        return new ArrayList<Map<String, Object>>();
    }
}