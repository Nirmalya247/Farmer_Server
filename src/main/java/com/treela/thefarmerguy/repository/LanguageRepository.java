package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.Language;
import com.treela.thefarmerguy.model.Sellrequest;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Integer> {
    public Language findById(long id);
}