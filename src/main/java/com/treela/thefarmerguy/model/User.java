package com.treela.thefarmerguy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
//@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(
        strategy= GenerationType.AUTO,
        generator="native"
    )
    @GenericGenerator(
        name = "native",
        strategy = "native"
    )
    @Column(name = "id", nullable = false)
    private long id;
    
    @Column(name = "phoneno", nullable = false)
    private long phoneno;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "pincode", nullable = false)
    private int pincode;
    
    @Column(name = "area", nullable = false)
    private String area;
    
    @Column(name = "isadmin", nullable = false)
    private int isadmin;
    
    public User() { }
    public User(long phoneno, String name, String password, int pincode, String area, int isadmin) {
        this.phoneno = phoneno;
        this.name = name;
        this.password = password;
        this.pincode = pincode;
        this.area = area;
        this.isadmin = isadmin;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public long getPincode() {
        return pincode;
    }
    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public long getIsadmin() {
        return isadmin;
    }
    public void setIsadmin(int isadmin) {
        this.isadmin = isadmin;
    }
    @Override
    public String toString() {
        return String.format(
                "User[phoneno=%d, name='%s', pincode='%d']",
                phoneno, name, pincode);
    }
}