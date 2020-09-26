/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "userid", nullable = false)
    private long userid;
    
    @Column(name = "device", nullable = false)
    private String device;
    
    @Column(name = "ipaddr", nullable = false)
    private String ipaddr;
    
    @Column(name = "createdat", nullable = false)
    private java.sql.Date createdat;
    
    
    public Session() {}
    public Session(long userid, String device, String ipaddr) {
        this.userid = userid;
        this.device = device;
        this.ipaddr = ipaddr;
        genId();
        setCreatedatNow();
    }
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void genId() {
        String t = "";
        String list = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_@";
        for (int i = 0; i < 32; i++) {
            Random rnd = new Random();
            t += list.charAt(rnd.nextInt(64));
        }
        this.id = t;
    }
    
    public long getUserid() {
        return userid;
    }
    public void setUserid(long userid) {
        this.userid = userid;
    }
    public String getDevice() {
        return device;
    }
    public void setdevice(String device) {
        this.device = device;
    }
    public String getIpaddr() {
        return ipaddr;
    }
    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }
    public java.sql.Date getCreatedat() {
        return createdat;
    }
    public void setCreatedat(java.sql.Date createdat) {
        this.createdat = createdat;
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