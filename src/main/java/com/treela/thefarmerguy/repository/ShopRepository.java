package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.Shop;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ShopRepository extends CrudRepository<Shop, Integer> {
    @Modifying
    @Query(value = "select sh.*,\n" +
        "post.state as state, post.district as district, post.taluk as taluk, post.division as division\n" +
        "from shops as sh\n" +
        "left join postcodes as post on post.pincode = sh.pincode\n" +
        "where sh.name rlike :text\n" +
        "order by id limit :offset, :limit",
           nativeQuery = true)
    public List<Map<String, Object>> findShop(
            @Param("text") String text,
            @Param("offset") long offset,
            @Param("limit") long limit);
    
    @Modifying
    @Query(value = "select sh.*,\n" +
                "post.state as state, post.district as district, post.taluk as taluk, post.division as division\n" +
                "from shops as sh\n" +
                "left join postcodes as post on post.pincode = sh.pincode\n" +
                "where sh.id = :id",
           nativeQuery = true)
    public List<Map<String, Object>> findSingleShop(
            @Param("id") long id);

    public Shop findById(long id);
    
    @Modifying
    @Query(value = "select\n" +
        "demand.id as demand_id,\n" +
        "demand.unit as demand_unit,\n" +
        "demand.quantity as demand_quantity,\n" +
        "demand.price as demand_price,\n" +
        "demand.description as demand_description,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = shop.name), shop.name) as shop_name,\n" +
        "shop.phoneno as shop_phoneno,\n" +
        "shop.alternateno as shop_alternateno,\n" +
        "shop.pincode as shop_pincode,\n" +
        "postcode.state as shop_state, postcode.district as shop_district, postcode.taluk as shop_taluk, postcode.division as shop_division,\n" +
        "shop.area as shop_area,\n" +
        "shop.detail as shop_detail,\n" +
        "(select exists (select * from sellrequests as sellreq where sellreq.demandid = demand.id and sellreq.userid = :userid)) as sellrequested\n" +
        "from demands as demand\n" +
        "left outer join shops as shop on shop.id = demand.shopid\n" +
        "left outer join postcodes as postcode on postcode.pincode = shop.pincode\n" +
        "where demand.productid = :productid\n" +
        "order by demand.price limit :offset, :limit",
           nativeQuery = true)
    public List<Map<String, Object>> getByProduct(
            @Param("language") String language,
            @Param("productid") long productid,
            @Param("offset") long offset,
            @Param("limit") long limit,
            @Param("userid") long userid);
    
    @Modifying
    @Query(value = "select\n" +
        "shop.id as id,\n" +
        "ifnull((select translated from translate where languagecode = :language and en = shop.name), shop.name) as name,\n" +
        "shop.phoneno as phoneno,\n" +
        "shop.alternateno as alternateno,\n" +
        "shop.pincode as pincode,\n" +
        "shop.area as area,\n" +
        "shop.detail as detail\n" +
        "from shops as shop\n" +
        "where shop.id = :id",
           nativeQuery = true)
    public List<Map<String, Object>> getSingleById(
            @Param("language") String language,
            @Param("id") long id);
   
}