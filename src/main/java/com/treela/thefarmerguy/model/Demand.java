/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "demands")
public class Demand {
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

    
    @Column(name = "productid", nullable = false)
    private long productid;
    
    @Column(name = "shopid", nullable = false)
    private long shopid;
    @Column(name = "unit", nullable = false)
    private String unit;
    
    @Column(name = "quantity", nullable = false)
    private long quantity;
    
    @Column(name = "price", nullable = false)
    private long price;
    
    @Column(name = "description", nullable = false)
    private String description;

    public Demand() {}

    public Demand(long productid, long shopid, String unit, long quantity, long price, String description) {
        this.productid = productid;
        this.shopid = shopid;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public Demand(long id, long productid, long shopid, String unit, long quantity, long price, String description) {
        this.id = id;
        this.productid = productid;
        this.shopid = shopid;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public long getShopid() {
        return shopid;
    }

    public void setShopid(long shopid) {
        this.shopid = shopid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
