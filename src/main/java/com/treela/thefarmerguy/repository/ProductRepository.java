/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.Product;
import com.treela.thefarmerguy.model.Shop;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nirmalya
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
    public List<Product> findAll();
    
    @Modifying
    @Query(value = "SELECT * FROM products",
           nativeQuery = true)
    public List<Product> getAll();
    
    @Modifying
    @Query(value = "select \n" +
        "product.productid as productid,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = product.name), product.name) as name,\n" +
        "product.description as description,\n" +
        "product.img as img,\n" +
        "(select min(price) from demands as prod where prod.productid = product.productid) as minprice,\n" +
        "(select max(price) from demands as prod where prod.productid = product.productid) as maxprice,\n" +
        "(select count(*) from demands as prod where prod.productid = product.productid) as countdemand\n" +
        "from products as product\n" +
        "where product.name rlike :text or\n" +
        "product.name in (select en from translate where translated rlike :text)\n" +
        "order by name limit :offset, :limit",
           nativeQuery = true)
    public List<Map<String, Object>> search(
            @Param("language") String language,
            @Param("text") String text,
            @Param("offset") long offset,
            @Param("limit") long limit);
    
    @Modifying
    @Query(value = "select\n" +
        "product.productid as productid,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = product.name), product.name) as name,\n" +
        "product.description as description,\n" +
        "product.img as img\n" +
        "from products as product\n" +
        "where product.productid = :id",
           nativeQuery = true)
    public List<Map<String, Object>> getSingleById(
            @Param("language") String language,
            @Param("id") long id);
}
