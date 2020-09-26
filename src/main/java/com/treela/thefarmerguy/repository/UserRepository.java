package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    public List<User> findByName(String userName);
    public User findById(long id);
    public User findByPhoneno(long phoneno);
    public boolean existsByIdAndPassword(long id, String password);
    public boolean existsByPhonenoAndPassword(long phoneno, String password);
    public boolean existsByIdAndIsadmin(long id, int isAdmin);
    @Modifying
    @Query(value = "SELECT * FROM postcodes where pincode = :pincode",
           nativeQuery = true)
    public List<Map<String, Object>> getPincode(
            @Param("pincode") int pincode);
}