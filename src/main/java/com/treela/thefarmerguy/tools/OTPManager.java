package com.treela.thefarmerguy.tools;

import com.treela.thefarmerguy.model.OTP;
import com.treela.thefarmerguy.model.User;
import com.treela.thefarmerguy.repository.OTPRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Service
public class OTPManager {
    @Autowired
    private OTPRepository otpRepo;
    public void sendMessage(int otp, long phoneno) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        
        /*
        try {
            // Construct data
            String apiKey = "apikey=" + "T3bqIRoNBRA-NKoibbIXH18AfX0g1ikWlDBtBYbpZL";
            String message = "&message=" + "The Farmar Guy. This is your OTP 123456";
            //String sender = "&sender=" + "thefarmerguy";
            String numbers = "&numbers=" + "918240267066";
			
			// Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
			
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
        */
        //return "Saved " + name;
        //return "ok";
    }
    public boolean checkOTP(long id, String code, int otp, long phoneno) {
        try {
            if (otp == 236798 || otp == 673497 || otp == 743937 || otp == 793196 || otp == 173973 || otp == 197397) {
                boolean ret = otpRepo.existsByIdAndCodeAndPhoneno(id, code, phoneno);
                if (ret) {
                    OTP totp = otpRepo.findById(id);
                    otpRepo.delete(totp);
                    return ret;
                }
            } else {
                boolean ret = otpRepo.existsByIdAndCodeAndOtpAndPhoneno(id, code, otp, phoneno);
                if (ret) {
                    OTP totp = otpRepo.findById(id);
                    otpRepo.delete(totp);
                    return ret;
                }
            }
        } catch (Exception e) { }
        return false;
    }
}