package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.Translate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TranslateRepository extends CrudRepository<Translate, Integer> {
    public Translate findById(long id);
    @Modifying
    @Query(value = "select ifnull(\n" +
            "(select product.name from products product\n" +
            "where product.name not in (select en from translate where languagecode = :languagecode) limit 1),\n" +
            "(select shop.name from shops shop\n" +
            "where shop.name not in (select en from translate where languagecode = :languagecode) limit 1)) as next",
           nativeQuery = true)
    public List<Map<String, Object>> nextTranslate(
            @Param("languagecode") String languagecode);
}