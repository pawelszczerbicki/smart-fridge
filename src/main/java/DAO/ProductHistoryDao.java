package DAO;

import model.ProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductHistoryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(ProductHistory productHistory) {
        mongoTemplate.save(productHistory);
    }

    public List<ProductHistory> findAll() {
        return mongoTemplate.findAll(ProductHistory.class);
    }
}
