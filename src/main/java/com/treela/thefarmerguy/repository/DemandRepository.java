/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.Demand;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DemandRepository extends CrudRepository<Demand, Integer> {
    public List<Demand> findByShopid(long shopid);
    public Demand findById(long id);
    
    
    @Modifying
    @Query(value = "select dm.*, pd.name as product_name, pd.description as product_description, pd.img as product_img,\n" +
            "(select exists(select * from sellrequests as sell where sell.demandid = dm.id and sell.userid = :userid)) as sellrequested\n" +
            "from demands as dm\n" +
            "left join products as pd on pd.productid = dm.productid\n" +
            "where dm.shopid = :shopid",
           nativeQuery = true)
    public List<Map<String, Object>> findAllByShopid(
            @Param("shopid") long shopid,
            @Param("userid") long userid);
}
