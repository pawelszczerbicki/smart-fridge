package DAO;

import model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public void save(Role role) {
        mongoTemplate.save(role);
    }

    public List<Role> findAll() {
        return mongoTemplate.findAll(Role.class);
    }

    public Role findById(Integer id) {
        return mongoTemplate.findById(id, Role.class);
    }

    public Long countAll() {
        return mongoTemplate.count(new Query(), Role.class);
    }
}
