/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.Demand;
import com.treela.thefarmerguy.model.Shop;
import com.treela.thefarmerguy.repository.DemandRepository;
import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.ShopRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import com.treela.thefarmerguy.tools.UserManager;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/demands")
public class DemandApi {
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

    @GetMapping(path="/getAllForShop")
    public List<Map<String, Object>> getAllForShop(
            @RequestParam(value = "shopid", defaultValue = "-1") long shopid,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        if (session_userid.equals("null") || session_userid.equals("")) {
            session_userid = param_session_userid;
        }
        long phnno = 0;
        
        try {
            phnno = Long.parseLong(session_userid);
        } catch(Exception e) {
            phnno = -1;
        }
        return demandRepo.findAllByShopid(shopid, phnno);
    }
    @PostMapping(path="/add")
    public Map<String, Object> addNewDemand(
            HttpServletResponse response,
            @RequestParam long productid,
            @RequestParam long shopid,
            @RequestParam String unit,
            @RequestParam long quantity,
            @RequestParam long price,
            @RequestParam String description,
            @RequestParam(value = "demandIndex", defaultValue = "-1") long demandIndex,
            @RequestParam(value = "id", defaultValue = "-1") long id,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        
        Demand d = new Demand(productid, shopid, unit, quantity, price, description);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            if (id > 0) d.setId(id);
            demandRepo.save(d);
            //else shopRepo.setFixedNamePhonenoAlternatenoPincodeLandmarkFor(name, phoneno, alternateno, pincode, landmark, id);
        }
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("id", d.getId());
        obj.put("productid", d.getProductid());
        obj.put("shopid", d.getShopid());
        obj.put("unit", d.getUnit());
        obj.put("quantity", d.getQuantity());
        obj.put("price", d.getPrice());
        obj.put("description", d.getDescription());
        obj.put("demandIndex", demandIndex);
        return obj;
    }
    @PostMapping(path="/delete")
    public Map<String, Object> delete(
            @RequestParam(value="demandid", defaultValue="0")long demandid,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        Map<String, Object> obj = new HashMap<String, Object>();
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            try {
                Demand d = demandRepo.findById(demandid);
                demandRepo.delete(d);
                obj.put("err", false);
                obj.put("msg", "demand deleted");
            } catch (Exception e) {
                obj.put("err", false);
                obj.put("msg", "some error");
            }
        } else {
            obj.put("err", false);
            obj.put("msg", "you are not admin");
        }
        return obj;
    }
}