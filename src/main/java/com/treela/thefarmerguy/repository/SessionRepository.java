package com.treela.thefarmerguy.repository;

import com.treela.thefarmerguy.model.Session;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface SessionRepository extends CrudRepository<Session, Integer> {

    public Session findById(String id);
    public long countById(String id);
    public boolean existsByIdAndUserid(String id, long userid);
    
    @Modifying
    @Query(value = "select sess.userid from session as sess\n" +
        "left join users as user on user.id = sess.userid\n" +
        "where sess.userid = :userid and sess.id = :id and user.isadmin > 1",
           nativeQuery = true)
    public List<Map<String, Object>> isAdmin(
            @Param("id") String id,
            @Param("userid") long userid);
    
}