/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.Product;
import com.treela.thefarmerguy.model.Shop;
import com.treela.thefarmerguy.repository.DemandRepository;
import com.treela.thefarmerguy.repository.ProductRepository;
import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.ShopRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import com.treela.thefarmerguy.tools.UserManager;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductApi {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private ShopRepository shopRepo;
    @Autowired
    private UserManager userManager;
    @Autowired
    private DemandRepository demandRepo;
    @Autowired
    private ProductRepository productRepo;

    @PostMapping(path="/getAll")
    public List<Product> getAll(
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        System.out.println("***************-products-" + session_id + " " + session_userid);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            System.out.println("***************-products-ok");
            return productRepo.getAll();
        }
        System.out.println("***************-products-done");
        return new ArrayList<Product>();
    }
    @PostMapping(path="/add")
    public Map<String, Object> addNewProduct(
            HttpServletResponse response,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String img,
            @RequestParam(value = "demandIndex", defaultValue = "-1") long demandIndex,
            @RequestParam(value = "productid", defaultValue = "-1") long productid,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        
        Product p = new Product(name, description, img);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            if (productid > 0) p.setProductid(productid);
            productRepo.save(p);
            //else shopRepo.setFixedNamePhonenoAlternatenoPincodeLandmarkFor(name, phoneno, alternateno, pincode, landmark, id);
        }
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("productid", p.getProductid());
        obj.put("name", p.getName());
        obj.put("description", p.getDescription());
        obj.put("img", p.getImg());
        obj.put("demandIndex", demandIndex);
        return obj;
    }
    @PostMapping(path="/search")
    public List<Map<String, Object>> search(
            @RequestParam(value="language", defaultValue="en")String language,
            @RequestParam(value="text", defaultValue=".*")String text,
            @RequestParam(value="page", defaultValue="0")long page,
            @RequestParam(value="limit", defaultValue="10")long limit) {
        long offset = page * limit;
        return productRepo.search(language, text, offset, limit);
    }
    @PostMapping(path="/getSingleById")
    public List<Map<String, Object>> getSingleById(
            @RequestParam(value="language", defaultValue="en")String language,
            @RequestParam(value="id", defaultValue="0")long id) {
        return productRepo.getSingleById(language, id);
    }
}