/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.Product;
import com.treela.thefarmerguy.model.Sellrequest;
import com.treela.thefarmerguy.repository.DemandRepository;
import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.ShopRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import com.treela.thefarmerguy.repository.SellrequestRepository;
import com.treela.thefarmerguy.tools.UserManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sellRequests")
public class SellRequestApi {
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
    private SellrequestRepository sellReqRepo;

    @PostMapping(path="/sendRequest")
    public Map<String, Object> sendRequest(
            @RequestParam(value = "demandID", defaultValue = "-1") long demandID,
            @RequestParam(value = "price", defaultValue = "-1") long price,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        if (session_userid.equals("null") || session_userid.equals("")) {
            session_userid = param_session_userid;
        }
        long userid = 0;
        try {
            userid = Long.parseLong(session_userid);
        } catch (Exception e) {
            userid = -1;
        }
        Sellrequest s = new Sellrequest(demandID, price, userid);
        //System.out.println("**********-" + demandID + " " + price + " " + userid);
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("err", false);
        obj.put("msg", "request added");
        if (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid)) {
            try {
                sellReqRepo.save(s);
            } catch (Exception e) {
                obj.put("err", true);
                obj.put("msg", "request could not be sent");
            }
        } else {
            obj.put("err", true);
            obj.put("msg", "not authorized");
        }
        return obj;
    }
    @PostMapping(path="/getByUser")
    public List<Map<String, Object>> getByUser(
            @RequestParam(value = "language", defaultValue = "en") String language,
            @RequestParam(value="page", defaultValue="0")long page,
            @RequestParam(value="limit", defaultValue="10")long limit,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        if (session_userid.equals("null") || session_userid.equals("")) {
            session_userid = param_session_userid;
        }
        long offset = page * limit;
        if (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid)) {
            try {
                return sellReqRepo.getByUser(session_userid, language, offset, limit);
            } catch (Exception e) { }
        }
        return new ArrayList<Map<String, Object>>();
    }
    @PostMapping(path="/getAll")
    public List<Map<String, Object>> getAll(
            @RequestParam(value = "language", defaultValue = "en") String language,
            @RequestParam(value="page", defaultValue="0")long page,
            @RequestParam(value="limit", defaultValue="10")long limit,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        if (session_userid.equals("null") || session_userid.equals("")) {
            session_userid = param_session_userid;
        }
        long offset = page * limit;
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            try {
                return sellReqRepo.getAll(language, offset, limit);
            } catch (Exception e) { }
        }
        return new ArrayList<Map<String, Object>>();
    }
    @PostMapping(path="/delete")
    public Map<String, Object> delete(
            @RequestParam(value = "id", defaultValue = "-1") long id,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        if (session_userid.equals("null") || session_userid.equals("")) {
            session_userid = param_session_userid;
        }
        Map<String, Object> obj = new HashMap<String, Object>();
        Sellrequest s = sellReqRepo.findById(id);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid) ||
                (userManager.checkLogin(session_id, session_userid, param_session_id, param_session_userid) &&
                s.getUserid() == Long.parseLong(session_userid))) {
            try {
                sellReqRepo.delete(s);
                obj.put("msg", "request deleted");
                obj.put("err", false);
                return obj;
            } catch (Exception e) {
                System.out.println("*****************************exception-sellRequests-delete-" + e);
            }
        }
        obj.put("msg", "could not delete");
        obj.put("err", true);
        return obj;
    }
}