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
@Table(name = "shops")
public class Shop {
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

    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "phoneno", nullable = false)
    private long phoneno;
    @Column(name = "alternateno", nullable = false)
    private long alternateno;
    
    @Column(name = "pincode", nullable = false)
    private int pincode;
    
    @Column(name = "area", nullable = false)
    private String area;
    
    @Column(name = "detail", nullable = false)
    private String detail;
    
    
    public Shop() {}
    public Shop(String name, long phoneno, long alternateno, int pincode, String area, String detail) {
        this.name = name;
        this.phoneno = phoneno;
        this.alternateno = alternateno;
        this.pincode = pincode;
        this.area = area;
        this.detail = detail;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getAlternateno() {
        return alternateno;
    }
    public void setAlternateno(long alternateno) {
        this.alternateno = alternateno;
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
    
    public long getPincode() {
        return pincode;
    }
    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    @Override
    public String toString() {
        return String.format(
                "User[phoneno=%d, name='%s', pincode='%d']",
                phoneno, name, pincode);
    }
}