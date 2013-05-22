package DAO;

import model.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FridgeDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Fridge> findAll(){
        return mongoTemplate.findAll(Fridge.class);
    }

    public void save(Fridge fridge){
        mongoTemplate.save(fridge);
    }

    public Fridge findByName(String name){
        return mongoTemplate.findOne(Query.query(Criteria.where("name").is(name)), Fridge.class);
    }
}
