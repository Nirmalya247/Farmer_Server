/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.treela.thefarmerguy.resource;

import com.treela.thefarmerguy.model.Language;
import com.treela.thefarmerguy.model.Product;
import com.treela.thefarmerguy.model.Translate;
import com.treela.thefarmerguy.repository.DemandRepository;
import com.treela.thefarmerguy.repository.LanguageRepository;
import com.treela.thefarmerguy.repository.ProductRepository;
import com.treela.thefarmerguy.repository.SessionRepository;
import com.treela.thefarmerguy.repository.ShopRepository;
import com.treela.thefarmerguy.repository.TranslateRepository;
import com.treela.thefarmerguy.repository.UserRepository;
import com.treela.thefarmerguy.tools.UserManager;
import java.util.ArrayList;
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
@RequestMapping("/language")
public class LanguageApi {
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
    @Autowired
    private LanguageRepository languageRepo;
    @Autowired
    private TranslateRepository translateRepo;
    @GetMapping(path="/getAllLanguages")
    public Iterable<Language> getAllLanguages() {
        return languageRepo.findAll();
    }
    @PostMapping(path="/addLanguage")
    public Language addLanguage(
            HttpServletResponse response,
            @RequestParam String languagecode,
            @RequestParam String name,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        Language l = new Language(languagecode, name);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            languageRepo.save(l);
            //else shopRepo.setFixedNamePhonenoAlternatenoPincodeLandmarkFor(name, phoneno, alternateno, pincode, landmark, id);
        }
        return l;
    }
    @PostMapping(path="/addTranslate")
    public Translate addTranslate(
            HttpServletResponse response,
            @RequestParam String languagecode,
            @RequestParam String en,
            @RequestParam String translated,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        Translate t = new Translate(languagecode, en, translated);
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            translateRepo.save(t);
            //else shopRepo.setFixedNamePhonenoAlternatenoPincodeLandmarkFor(name, phoneno, alternateno, pincode, landmark, id);
        }
        return t;
    }
    @PostMapping(path="/nextTranslate")
    public List<Map<String, Object>> nextTranslate(
            HttpServletResponse response,
            @RequestParam String languagecode,
            @CookieValue(value = "session_id", defaultValue = "null") String session_id,
            @CookieValue(value = "session_userid", defaultValue = "null") String session_userid,
            @RequestParam(value = "session_id", defaultValue = "null") String param_session_id,
            @RequestParam(value = "session_userid", defaultValue = "null") String param_session_userid) {
        if (userManager.isAdmin(session_id, session_userid, param_session_id, param_session_userid)) {
            try {
                return translateRepo.nextTranslate(languagecode);
            } catch (Exception e) {
                System.out.println("***************-language-nextTranslate-" + e);
            }
            //else shopRepo.setFixedNamePhonenoAlternatenoPincodeLandmarkFor(name, phoneno, alternateno, pincode, landmark, id);
        }
        return null;
    }
}