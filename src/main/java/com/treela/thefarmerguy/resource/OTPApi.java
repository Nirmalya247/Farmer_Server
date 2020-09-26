/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.OTP;
import com.treela.thefarmerguy.model.User;
import com.treela.thefarmerguy.repository.OTPRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/otp")
public class OTPApi {
    @Autowired
    private OTPRepository otpRepo;
    @Autowired
    private UserRepository userRepo;
    @PostMapping(path="/sendOTP") // Map ONLY POST Requests
    public Map<String, Object> addNewUser(
            HttpServletRequest request,
            @RequestParam(value = "phoneno", defaultValue = "0") long phoneno) {
        Map<String, Object> obj = new HashMap<String, Object>();
        OTP tOtp = new OTP(phoneno, request.getRemoteAddr());
        try {
            otpRepo.save(tOtp);
            obj.put("err", false);
            obj.put("msg", "sent");
            obj.put("id", tOtp.getId());
            obj.put("code", tOtp.getCode());
            return obj;
        } catch (Exception e) { }
        obj.put("err", true);
        obj.put("msg", "unknown error");
        //obj.put("id", tOtp.getId());
        //obj.put("code", tOtp.getCode());
        return obj;
    }
    // remove
    @PostMapping(path="/checkOTP") // Map ONLY POST Requests
    public Map<String, Object> checkOTP(
            HttpServletRequest request,
            @RequestParam long otp_id,
            @RequestParam String otp_code,
            @RequestParam int otp_otp,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        Map<String, Object> obj = new HashMap<String, Object>();
        if (session_userid.equals("null")) {
            session_userid = param_session_userid;
        }
        long phoneno = Long.parseLong(session_userid);
        try {
            boolean ret = otpRepo.existsByIdAndCodeAndOtpAndPhoneno(otp_id, otp_code, otp_otp, phoneno);
            obj.put("err", false);
            obj.put("otpok", ret);
            return obj;
        } catch (Exception e) { }
        obj.put("err", true);
        obj.put("otpok", false);
        return obj;
    }
    // remove
}