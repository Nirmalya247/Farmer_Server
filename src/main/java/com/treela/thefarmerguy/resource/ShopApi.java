package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.Demand;
import com.treela.thefarmerguy.model.Session;
import com.treela.thefarmerguy.model.Shop;
import com.treela.thefarmerguy.model.User;
import com.treela.thefarmerguy.repository.DemandRepository;
import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.ShopRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import com.treela.thefarmerguy.tools.UserManager;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/shops")
public class ShopApi {
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
    
    @PostMapping(path="/get")
    public List<Map<String, Object>> get(
            @RequestParam(value="text", defaultValue=".*")String text,
            @RequestParam(value="page", defaultValue="0")long page,
            @RequestParam(value="limit", defaultValue="10")long limit,
            @RequestParam(value="sort", defaultValue="asc")String sort) {
        
        // System.out.println("page " + page + " limit " + limit);
        long offset = page * limit;
        return shopRepo.findShop(text, offset, limit);
    }
    
    @PostMapping(path="/add")
    public Shop addNewShop(
            HttpServletResponse response,
            @RequestParam String name,
            @RequestParam long phoneno,
            @RequestParam long alternateno,
            @RequestParam int pincode,
            @RequestParam String landmark,
            @RequestParam String detail,
            @RequestParam(value = "id", defaultValue = "-1") long id,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        
        Shop s = new Shop(name, phoneno, alternateno, pincode, landmark, detail);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            if (id > 0) s.setId(id);
            shopRepo.save(s);
            //else shopRepo.setFixedNamePhonenoAlternatenoPincodeLandmarkFor(name, phoneno, alternateno, pincode, landmark, id);
        }
        return s;
    }
    @GetMapping(path="/getSingle")
    public List<Map<String, Object>> getSingle(
            @RequestParam(value = "id", defaultValue = "-1") long id) {
        return shopRepo.findSingleShop(id);
    }
    @PostMapping(path="/getByProduct")
    public List<Map<String, Object>> getByProduct(
            @RequestParam(value="language", defaultValue="en")String language,
            @RequestParam(value="text", defaultValue=".*")String text,
            @RequestParam(value="productid", defaultValue="0")long productid,
            @RequestParam(value="page", defaultValue="0")long page,
            @RequestParam(value="limit", defaultValue="10")long limit,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        long offset = page * limit;
        if (session_userid.equals("null")) {
            session_userid = param_session_userid;
        }
        long userid = 0;
        if (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid)) {
            userid = Long.parseLong(session_userid);
        }
        return shopRepo.getByProduct(language, productid, offset, limit, userid);
    }
    @PostMapping(path="/getSingleById")
    public List<Map<String, Object>> getSingleById(
            @RequestParam(value="language", defaultValue="en")String language,
            @RequestParam(value="id", defaultValue="0")long id) {
        return shopRepo.getSingleById(language, id);
    }
    @PostMapping(path="/delete")
    public Map<String, Object> delete(
            @RequestParam(value="shopid", defaultValue="0")long shopid,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        Map<String, Object> obj = new HashMap<String, Object>();
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            try {
                Shop s = shopRepo.findById(shopid);
                shopRepo.delete(s);
                obj.put("err", false);
                obj.put("msg", "shop deleted");
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