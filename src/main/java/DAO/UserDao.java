package DAO;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public User findById(String id){
        return mongoTemplate.findById(id,User.class);
    }

    public User findByUsername(String username){
        return mongoTemplate.findOne(Query.query(Criteria.where("login").is(username)),User.class);
    }

    public List<User> findUsernameByRegex(String username, int limit){
        return mongoTemplate.find(Query.query(Criteria.where("login").regex(username)).limit(limit),User.class);
    }

    public List<User> findAll(){
        return mongoTemplate.findAll(User.class);
    }

    public Long countAll(){
        return mongoTemplate.count(new Query(), User.class);
    }

    public void save(User user){
        mongoTemplate.save(user);
    }
    public void remove(User user){
        mongoTemplate.remove(user);
    }
}
