package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.OTP;
import com.treela.thefarmerguy.model.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface OTPRepository extends CrudRepository<OTP, Integer> {
    public OTP findById(long key);
    public boolean existsByIdAndCodeAndOtpAndPhoneno(long id, String code, int otp, long phoneno);
}