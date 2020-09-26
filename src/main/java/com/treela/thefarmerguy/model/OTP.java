package com.treela.thefarmerguy.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Random;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "otps")
//@EntityListeners(AuditingEntityListener.class)
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    
    @Column(name = "phoneno", nullable = false)
    private long phoneno;
    
    @Column(name = "otp", nullable = false)
    private int otp;
    
    @Column(name = "ipaddr", nullable = false)
    private String ipaddr;
    
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "createdat", nullable = false)
    private java.sql.Date createdat;

    public OTP() {
    }

    public OTP(long phoneno, String ipaddr) {
        this.phoneno = phoneno;
        this.ipaddr = ipaddr;
        genOtp();
        genCode();
        setCreatedatNow();
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getPhoneno() {
        return phoneno;
    }
    public void setPhoneno(long phoneno) {
        this.phoneno = phoneno;
    }
    public int getOtp() {
        return otp;
    }
    public void setOtp(int otp) {
        this.otp = otp;
    }
    public String getIpaddr() {
        return ipaddr;
    }
    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public java.sql.Date getCreatedat() {
        return createdat;
    }
    public void setCreatedat(java.sql.Date createdat) {
        this.createdat = createdat;
    }
    public void genOtp() {
        Random rnd = new Random();
        this.otp = rnd.nextInt(999999);
    }
    public void genCode() {
        String t = "";
        String list = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_@";
        for (int i = 0; i < 32; i++) {
            Random rnd = new Random();
            t += list.charAt(rnd.nextInt(64));
        }
        this.code = t;
    }
    public void setCreatedatNow() {
        this.createdat = new java.sql.Date(new Date().getTime());
    }
    
    @Override
    public String toString() {
        return "Temporary";
        //return String.format("User[phoneno=%d, name='%s', pincode='%d']", phoneno, name, pincode);
    }
}