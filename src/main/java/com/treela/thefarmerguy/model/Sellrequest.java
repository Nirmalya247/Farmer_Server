/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.model;

import java.util.Date;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sellrequests")
public class Sellrequest {
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
    
    @Column(name = "demandid", nullable = false)
    private long demandid;
    
    @Column(name = "userid", nullable = false)
    private long userid;
    
    @Column(name = "price", nullable = false)
    private long price;
    
    @Column(name = "createdat", nullable = false)
    private java.sql.Date createdat;

    public Sellrequest(long demandid, long price, long userid) {
        this.demandid = demandid;
        this.price = price;
        this.userid = userid;
        setCreatedatNow();
    }

    public Sellrequest() {
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDemandid() {
        return demandid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setDemandid(long demandid) {
        this.demandid = demandid;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
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