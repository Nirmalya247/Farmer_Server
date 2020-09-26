/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.OTP;
import com.treela.thefarmerguy.model.Sellrequest;
import com.treela.thefarmerguy.model.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SellrequestRepository extends CrudRepository<Sellrequest, Integer> {
    public Sellrequest findById(long id);
    public List<Sellrequest> findByUserid(long userid);
    long deleteById(long id);
    // get by user
    @Modifying
    @Query(value = "select selreq.id, selreq.createdat,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = shop.name), shop.name) as shop_name,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = product.name), product.name) as product_name,\n" +
        "shop.phoneno as shop_phoneno,\n" +
        "shop.alternateno as shop_alternateno,\n" +
        "shop.pincode as shop_pincode,\n" +
        "shopPostcode.state as shop_state, shopPostcode.district as shop_district, shopPostcode.taluk as shop_taluk, shopPostcode.division as shop_division,\n" +
        "shop.area as shop_area,\n" +
        "user.name as user_name,\n" +
        "user.phoneno as user_phoneno,\n" +
        "user.pincode as user_pincode,\n" +
        "userPostcode.state as user_state, userPostcode.district as user_district, userPostcode.taluk as user_taluk, userPostcode.division as user_division,\n" +
        "user.area as user_area\n" +
        "from sellrequests as selreq\n" +
        "left outer join demands as demand on demand.id = selreq.demandid\n" +
        "left outer join shops as shop on shop.id = demand.shopid\n" +
        "left outer join postcodes as shopPostcode on shopPostcode.pincode = shop.pincode\n" +
        "left outer join products as product on product.productid = demand.productid\n" +
        "left outer join users as user on user.id = selreq.userid\n" +
        "left outer join postcodes as userPostcode on userPostcode.pincode = user.pincode\n" +
        "where selreq.userid = :userid order by createdat desc limit :offset, :limit",
           nativeQuery = true)
    public List<Map<String, Object>> getByUser(
            @Param("userid") String userid,
            @Param("language") String language,
            @Param("offset") long offset,
            @Param("limit") long limit);
    // get all
    @Modifying
    @Query(value = "select selreq.id, selreq.createdat,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = shop.name), shop.name) as shop_name,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = product.name), product.name) as product_name,\n" +
        "shop.phoneno as shop_phoneno,\n" +
        "shop.alternateno as shop_alternateno,\n" +
        "shop.pincode as shop_pincode,\n" +
        "shopPostcode.state as shop_state, shopPostcode.district as shop_district, shopPostcode.taluk as shop_taluk, shopPostcode.division as shop_division,\n" +
        "shop.area as shop_area,\n" +
        "user.name as user_name,\n" +
        "user.phoneno as user_phoneno,\n" +
        "user.pincode as user_pincode,\n" +
        "userPostcode.state as user_state, userPostcode.district as user_district, userPostcode.taluk as user_taluk, userPostcode.division as user_division,\n" +
        "user.area as user_area\n" +
        "from sellrequests as selreq\n" +
        "left outer join demands as demand on demand.id = selreq.demandid\n" +
        "left outer join shops as shop on shop.id = demand.shopid\n" +
        "left outer join postcodes as shopPostcode on shopPostcode.pincode = shop.pincode\n" +
        "left outer join products as product on product.productid = demand.productid\n" +
        "left outer join users as user on user.id = selreq.userid\n" +
        "left outer join postcodes as userPostcode on userPostcode.pincode = user.pincode\n" +
        "order by createdat desc limit :offset, :limit",
           nativeQuery = true)
    public List<Map<String, Object>> getAll(
            @Param("language") String language,
            @Param("offset") long offset,
            @Param("limit") long limit);
}